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

package org.radarcns.passive.bittium;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.SparseArray;

import org.radarcns.android.device.AbstractDeviceManager;
import org.radarcns.android.device.DeviceStatusListener;
import org.radarcns.bittium.faros.FarosDevice;
import org.radarcns.bittium.faros.FarosDeviceListener;
import org.radarcns.bittium.faros.FarosSdkFactory;
import org.radarcns.bittium.faros.FarosSdkListener;
import org.radarcns.bittium.faros.FarosSdkManager;
import org.radarcns.bittium.faros.FarosSettings;
import org.radarcns.kafka.ObservationKey;
import org.radarcns.passive.emotion.EmotionFarosAcceleration;
import org.radarcns.passive.emotion.EmotionFarosBatteryLevel;
import org.radarcns.passive.emotion.EmotionFarosEcg;
import org.radarcns.passive.emotion.EmotionFarosInterBeatInterval;
import org.radarcns.passive.emotion.EmotionFarosTemperature;
import org.radarcns.topic.AvroTopic;
import org.radarcns.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class FarosDeviceManager extends AbstractDeviceManager<FarosService, FarosDeviceStatus> implements FarosDeviceListener, FarosSdkListener {
    private static final Logger logger = LoggerFactory.getLogger(FarosDeviceManager.class);

    private final AvroTopic<ObservationKey, EmotionFarosAcceleration> accelerationTopic;
    private final AvroTopic<ObservationKey, EmotionFarosEcg> ecgTopic;
    private final AvroTopic<ObservationKey, EmotionFarosInterBeatInterval> ibiTopic;
    private final AvroTopic<ObservationKey, EmotionFarosTemperature> temperatureTopic;
    private final AvroTopic<ObservationKey, EmotionFarosBatteryLevel> batteryTopic;
    private final HandlerThread mHandlerThread;
    private final static SparseArray<DeviceStatusListener.Status> STATUS_MAP = new SparseArray<>();
    static {
        STATUS_MAP.put(FarosDeviceListener.IDLE, DeviceStatusListener.Status.CONNECTING);
        STATUS_MAP.put(FarosDeviceListener.CONNECTING, DeviceStatusListener.Status.CONNECTING);
        STATUS_MAP.put(FarosDeviceListener.DISCONNECTED, DeviceStatusListener.Status.DISCONNECTED);
        STATUS_MAP.put(FarosDeviceListener.DISCONNECTING, DeviceStatusListener.Status.DISCONNECTED);
        STATUS_MAP.put(FarosDeviceListener.MEASURING, DeviceStatusListener.Status.CONNECTED);
    }

    private final static SparseArray<String> FAROS_TYPE_MAP = new SparseArray<>();
    static {
        FAROS_TYPE_MAP.put(FarosDevice.FAROS_90, "FAROS_90");
        FAROS_TYPE_MAP.put(FarosDevice.FAROS_180, "FAROS_180");
        FAROS_TYPE_MAP.put(FarosDevice.FAROS_360, "FAROS_360");
    }

    private final static SparseArray<Float> FAROS_BATTERY_STATUS = new SparseArray<>();
    static {
        FAROS_BATTERY_STATUS.put(BATTERY_STATUS_CRITICAL, 0.05f);
        FAROS_BATTERY_STATUS.put(BATTERY_STATUS_LOW, 0.175f);
        FAROS_BATTERY_STATUS.put(BATTERY_STATUS_MEDIUM, 0.5f);
        FAROS_BATTERY_STATUS.put(BATTERY_STATUS_FULL, 0.875f);
    }

    private final FarosSdkFactory farosFactory;

    private Pattern[] acceptableIds;

    private FarosDevice faros;
    private FarosSdkManager apiManager;
    private FarosSettings settings;

    public FarosDeviceManager(FarosService service, FarosSdkFactory factory, FarosSettings settings) {
        super(service);

        this.farosFactory = factory;
        this.settings = settings;

        accelerationTopic = createTopic("android_emotion_faros_acceleration", EmotionFarosAcceleration.class);
        ecgTopic = createTopic("android_emotion_faros_ecg", EmotionFarosEcg.class);
        ibiTopic = createTopic("android_emotion_faros_inter_beat_interval", EmotionFarosInterBeatInterval.class);
        temperatureTopic = createTopic("android_emotion_faros_temperature", EmotionFarosTemperature.class);
        batteryTopic = createTopic("android_emotion_faros_battery_level", EmotionFarosBatteryLevel.class);

        mHandlerThread = new HandlerThread("Faros");

        synchronized (this) {
            this.acceptableIds = null;
        }
        updateStatus(DeviceStatusListener.Status.READY);
    }

    @Override
    public void start(@NonNull final Set<String> accepTopicIds) {
        logger.info("Faros searching for device.");

        apiManager = farosFactory.createSdkManager(getService());

        try {
            apiManager.startScanning(this);
        } catch (IllegalStateException ex) {
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
        if (status == FarosDeviceListener.IDLE) {
            applySettings(this.settings);
            faros.startMeasurements();
        }
        updateStatus(radarStatus);
    }

    @Override
    public void onDeviceScanned(FarosDevice device) {
        if (faros != null) {
            return;
        }
        if (acceptableIds.length == 0 || Strings.findAny(acceptableIds, device.getName())) {
            mHandlerThread.start();
            device.connect(this, new Handler(mHandlerThread.getLooper()));
            apiManager.stopScanning();
            synchronized (this) {
                this.faros = device;
                updateStatus(DeviceStatusListener.Status.CONNECTING);
            }
            Map<String, String> attributes = new ArrayMap<>(3);
            attributes.put("name", faros.getName());
            attributes.put("type", FAROS_TYPE_MAP.get(faros.getType(), "unknown"));
            getService().registerDevice(faros.getName(), attributes);
        }
    }

    @Override
    public void didReceiveAcceleration(double timestamp, float x, float y, float z) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(accelerationTopic, new EmotionFarosAcceleration(timestamp, timeReceived, x, y, z));
    }

    @Override
    public void didReceiveTemperature(double timestamp, float temperature) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(temperatureTopic, new EmotionFarosTemperature(timestamp, timeReceived, temperature));
    }

    @Override
    public void didReceiveInterBeatInterval(double timestamp, float interBeatInterval) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(ibiTopic, new EmotionFarosInterBeatInterval(timestamp, timeReceived, interBeatInterval));
    }

    @Override
    public void didReceiveEcg(double timestamp, float[] channels) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        Float channelOne = channels[0];
        Float channelTwo = channels.length > 1 ? channels[1] : null;
        Float channelThree = channels.length > 2 ? channels[2] : null;

        send(ecgTopic, new EmotionFarosEcg(timestamp, timeReceived, channelOne, channelTwo, channelThree));
    }

    @Override
    public void didReceiveBatteryStatus(double timestamp, int status) {
        // only send approximate battery levels if the battery level interval is disabled.
        if (settings.getBatteryLevelInterval() != 0) {
            return;
        }
        double timeReceived = System.currentTimeMillis() / 1000d;
        float level = FAROS_BATTERY_STATUS.get(status, -1f);
        if (level == -1f) {
            logger.warn("Unknown battery status {} passed", status);
            return;
        }
        send(batteryTopic, new EmotionFarosBatteryLevel(timestamp, timeReceived, level));
    }

    @Override
    public void didReceiveBatteryLevel(double timestamp, float level) {
        double timeReceived = System.currentTimeMillis() / 1000d;
        send(batteryTopic, new EmotionFarosBatteryLevel(timestamp, timeReceived, level));
    }

    public void applySettings(FarosSettings settings) {
        FarosDevice device;
        synchronized (this) {
            device = faros;
            this.settings = settings;
        }
        if (device != null) {
            device.apply(settings);
        }
    }
}
