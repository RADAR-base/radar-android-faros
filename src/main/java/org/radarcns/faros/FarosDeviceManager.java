/*
 * Copyright 2017 The Hyve
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.radarcns.faros;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.SparseArray;

import org.radarcns.android.device.AbstractDeviceManager;
import org.radarcns.android.device.DeviceStatusListener;
import org.radarcns.kafka.ObservationKey;
import org.radarcns.passive.faros.FarosAcceleration;
import org.radarcns.passive.faros.FarosBatteryLevel;
import org.radarcns.passive.faros.FarosEcg;
import org.radarcns.passive.faros.FarosInterBeatInterval;
import org.radarcns.passive.faros.FarosTemperature;
import org.radarcns.topic.AvroTopic;
import org.radarcns.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class FarosDeviceManager extends AbstractDeviceManager<FarosService, FarosDeviceStatus> implements FarosDataDelegate, FarosStatusListener {
    private static final Logger logger = LoggerFactory.getLogger(FarosDeviceManager.class);

    private final AvroTopic<ObservationKey, FarosAcceleration> accelerationTopic;
    private final AvroTopic<ObservationKey, FarosEcg> ecgTopic;
    private final AvroTopic<ObservationKey, FarosInterBeatInterval> ibiTopic;
    private final AvroTopic<ObservationKey, FarosTemperature> temperatureTopic;
    private final AvroTopic<ObservationKey, FarosBatteryLevel> batteryTopic;
    private final HandlerThread mHandlerThread;
    private final static SparseArray<DeviceStatusListener.Status> STATUS_MAP = new SparseArray<>();
    static {
        STATUS_MAP.put(FarosStatusListener.CONNECTED, DeviceStatusListener.Status.CONNECTED);
        STATUS_MAP.put(FarosStatusListener.CONNECTING, DeviceStatusListener.Status.CONNECTING);
        STATUS_MAP.put(FarosStatusListener.DISCONNECTED, DeviceStatusListener.Status.DISCONNECTED);
        STATUS_MAP.put(FarosStatusListener.DISCONNECTING, DeviceStatusListener.Status.DISCONNECTED);
        STATUS_MAP.put(FarosStatusListener.READY, DeviceStatusListener.Status.READY);
    }

    private final static SparseArray<String> FAROS_TYPE_MAP = new SparseArray<>();
    static {
        FAROS_TYPE_MAP.put(FarosDevice.FAROS_180, "FAROS_180");
        FAROS_TYPE_MAP.put(FarosDevice.FAROS_360, "FAROS_360");
    }

    private Pattern[] acceptableIds;

    private FarosDevice faros;
    private FarosApiManager apiManager;

    public FarosDeviceManager(FarosService service) {
        super(service);

        accelerationTopic = createTopic("android_faros_acceleration", FarosAcceleration.class);
        ecgTopic = createTopic("android_faros_ecg", FarosEcg.class);
        ibiTopic = createTopic("android_faros_inter_beat_interval", FarosInterBeatInterval.class);
        temperatureTopic = createTopic("android_faros_temperature", FarosTemperature.class);
        batteryTopic = createTopic("android_faros_battery_level", FarosBatteryLevel.class);

        mHandlerThread = new HandlerThread("Faros");

        synchronized (this) {
            this.acceptableIds = null;
        }
    }

    @Override
    public void start(@NonNull final Set<String> accepTopicIds) {
        logger.info("Faros searching for device.");

        mHandlerThread.start();

        apiManager = new FarosApiFactory().createApiManager(this, this,
                new Handler(mHandlerThread.getLooper()));

        try {
            apiManager.startScanning();
        } catch (IOException ex) {
            logger.error("Failed to start scanning", ex);
            close();
        }
        synchronized (this) {
            this.acceptableIds = Strings.containsPatterns(accepTopicIds);
        }
    }

    protected synchronized void updateStatus(DeviceStatusListener.Status status) {
        super.updateStatus(status);
    }

    @Override
    protected void registerDeviceAtReady() {
        // custom at device
    }

    @Override
    public void close() {
        if (isClosed()) {
            return;
        }
        logger.info("Faros BT Closing device {}", this);
        try {
            super.close();
            if (faros != null) {
                faros.close();
            }
        } catch (IOException e2) {
            logger.error("Faros socket close failed");
        } catch (NullPointerException npe) {
            logger.info("Can't close an unopened socket");
        }
        mHandlerThread.quitSafely();
        try {
            apiManager.close();
        } catch (IOException e) {
            logger.warn("Failed to close Faros API manager", e);
        }

        updateStatus(DeviceStatusListener.Status.DISCONNECTED);
    }

    @Override
    public void onStatusUpdate(int status) {
        DeviceStatusListener.Status radarStatus = STATUS_MAP.get(status);
        if (radarStatus == null) {
            logger.warn("Faros status {} is unknown", status);
        }
        updateStatus(radarStatus);
    }

    @Override
    public void onDeviceScanned(FarosDevice device) {
        if (faros != null) {
            return;
        }
        if (Strings.findAny(acceptableIds, device.getName())) {
            try {
                device.connect();
                apiManager.stopScanning();
                this.faros = device;
                updateStatus(DeviceStatusListener.Status.CONNECTING);
                Map<String, String> attributes = new ArrayMap<>(3);
                attributes.put("name", faros.getName());
                attributes.put("type", FAROS_TYPE_MAP.get(faros.getType(), "unknown"));
                getService().registerDevice(faros.getName(), attributes);
            } catch (IOException ex) {
                logger.error("Failed to connect to Faros {}", device, ex);
            }
        }
    }

    @Override
    public void didReceiveAcceleration(double timestamp, float x, float y, float z) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(accelerationTopic, new FarosAcceleration(timestamp, timeReceived, x, y, z));
    }

    @Override
    public void didReceiveTemperature(double timestamp, float temperature) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(temperatureTopic, new FarosTemperature(timestamp, timeReceived, temperature));
    }

    @Override
    public void didReceiveInterBeatInterval(double timestamp, float interBeatInterval) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(ibiTopic, new FarosInterBeatInterval(timestamp, timeReceived, interBeatInterval));
    }

    @Override
    public void didReceiveEcg(double timestamp, float[] channels) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(ecgTopic, new FarosEcg(timestamp, timeReceived, channels[0], channels[1], channels[2]));
    }

    @Override
    public void didReceiveBatteryStatus(double timestamp, int status) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(batteryTopic, new FarosBatteryLevel(timestamp, timeReceived, status));
    }
}
