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

import android.os.Bundle;
import android.support.annotation.NonNull;

import org.radarcns.android.RadarConfiguration;
import org.radarcns.android.device.DeviceServiceProvider;

import java.util.Arrays;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.BLUETOOTH_ADMIN;

public class FarosProvider extends DeviceServiceProvider<FarosDeviceStatus> {
    private static final String FAROS_PREFIX = "org.radarcns.passive.bittium.";

    private static final String ACC_RATE = "bittium_faros_acceleration_rate";
    private static final int ACC_RATE_DEFAULT = 25;

    private static final String ACC_RESOLUTION = "bittium_faros_acceleration_resolution";
    private static final float ACC_RESOLUTION_DEFAULT = 0.001f;

    private static final String ECG_RATE = "bittium_faros_ecg_rate";
    private static final int ECG_RATE_DEFAULT = 125;

    private static final String ECG_RESOLUTION = "bittium_faros_ecg_resolution";
    private static final float ECG_RESOLUTION_DEFAULT = 1.0f;

    private static final String ECG_CHANNELS = "bittium_faros_ecg_channels";
    private static final int ECG_CHANNELS_DEFAULT = 1;

    private static final String ECG_FILTER_FREQUENCY = "bittium_faros_ecg_filter_frequency";
    private static final float ECG_FILTER_FREQUENCY_DEFAULT = 0.05f;

    private static final String TEMP_ENABLE = "bittium_faros_temperature_enable";
    private static final boolean TEMP_ENABLE_DEFAULT = true;

    private static final String IBI_ENABLE = "bittium_faros_inter_beat_interval_enable";
    private static final boolean IBI_ENABLE_DEFAULT = true;

    public static final String ACC_RATE_KEY = FAROS_PREFIX + ACC_RATE;
    public static final String ACC_RESOLUTION_KEY = FAROS_PREFIX + ACC_RESOLUTION;
    public static final String ECG_RATE_KEY = FAROS_PREFIX + ECG_RATE;
    public static final String ECG_RESOLUTION_KEY = FAROS_PREFIX + ECG_RESOLUTION;
    public static final String ECG_CHANNELS_KEY = FAROS_PREFIX + ECG_CHANNELS;
    public static final String ECG_FILTER_FREQUENCY_KEY = FAROS_PREFIX + ECG_FILTER_FREQUENCY;
    public static final String IBI_ENABLE_KEY = FAROS_PREFIX + IBI_ENABLE;
    public static final String TEMP_ENABLE_KEY = FAROS_PREFIX + TEMP_ENABLE;

    @Override
    public String getDescription() {
        return getRadarService().getString(R.string.farosDescription);
    }

    @Override
    public Class<?> getServiceClass() {
        return FarosService.class;
    }

    @Override
    public boolean hasDetailView() {
        return true;
    }

    @Override
    public List<String> needsPermissions() {
        return Arrays.asList(ACCESS_COARSE_LOCATION, BLUETOOTH, BLUETOOTH_ADMIN);
    }

    @NonNull
    @Override
    public String getDeviceProducer() {
        return "Bittium";
    }

    @NonNull
    @Override
    public String getDeviceModel() {
        return "Faros";
    }

    @NonNull
    @Override
    public String getVersion() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public String getDisplayName() {
        return getRadarService().getString(R.string.farosLabel);
    }

    @Override
    protected void configure(Bundle bundle) {
        super.configure(bundle);
        RadarConfiguration config = getConfig();
        bundle.putInt(ACC_RATE_KEY, config.getInt(ACC_RATE, ACC_RATE_DEFAULT));
        bundle.putFloat(ACC_RESOLUTION_KEY, config.getFloat(ACC_RESOLUTION, ACC_RESOLUTION_DEFAULT));
        bundle.putInt(ECG_RATE_KEY, config.getInt(ECG_RATE, ECG_RATE_DEFAULT));
        bundle.putFloat(ECG_RESOLUTION_KEY, config.getFloat(ECG_RESOLUTION, ECG_RESOLUTION_DEFAULT));
        bundle.putInt(ECG_CHANNELS_KEY, config.getInt(ECG_CHANNELS, ECG_CHANNELS_DEFAULT));
        bundle.putFloat(ECG_FILTER_FREQUENCY_KEY, config.getFloat(ECG_FILTER_FREQUENCY, ECG_FILTER_FREQUENCY_DEFAULT));
        bundle.putBoolean(TEMP_ENABLE_KEY, config.getBoolean(TEMP_ENABLE, TEMP_ENABLE_DEFAULT));
        bundle.putBoolean(IBI_ENABLE_KEY, config.getBoolean(IBI_ENABLE, IBI_ENABLE_DEFAULT));
    }
}
