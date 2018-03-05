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

import org.radarcns.android.device.DeviceService;
import org.radarcns.bittium.faros.FarosSdkFactory;
import org.radarcns.bittium.faros.FarosSettings;

import static org.radarcns.passive.bittium.FarosProvider.ACC_RATE_KEY;
import static org.radarcns.passive.bittium.FarosProvider.ACC_RESOLUTION_KEY;
import static org.radarcns.passive.bittium.FarosProvider.ECG_CHANNELS_KEY;
import static org.radarcns.passive.bittium.FarosProvider.ECG_FILTER_FREQUENCY_KEY;
import static org.radarcns.passive.bittium.FarosProvider.ECG_RATE_KEY;
import static org.radarcns.passive.bittium.FarosProvider.ECG_RESOLUTION_KEY;
import static org.radarcns.passive.bittium.FarosProvider.IBI_ENABLE_KEY;
import static org.radarcns.passive.bittium.FarosProvider.TEMP_ENABLE_KEY;

/**
 * A service that manages a FarosDeviceManager and a TableDataHandler to send store the data of a
 * Faros bluetooth connection
 */
public class FarosService extends DeviceService<FarosDeviceStatus> {
    private FarosSdkFactory farosFactory;
    private FarosSettings settings;

    private void initFaros() {
        if (farosFactory == null) {
            farosFactory = new FarosSdkFactory();
            settings = farosFactory.defaultSettingsBuilder().build();
        }
    }

    @Override
    protected FarosDeviceManager createDeviceManager() {
        initFaros();
        return new FarosDeviceManager(this, farosFactory, settings);
    }

    @Override
    protected FarosDeviceStatus getDefaultState() {
        return new FarosDeviceStatus();
    }

    @Override
    protected void onInvocation(@NonNull Bundle bundle) {
        super.onInvocation(bundle);

        initFaros();
        settings = farosFactory.defaultSettingsBuilder()
                .accelerometerRate(bundle.getInt(ACC_RATE_KEY))
                .accelerometerResolution(bundle.getFloat(ACC_RESOLUTION_KEY))
                .ecgRate(bundle.getInt(ECG_RATE_KEY))
                .ecgResolution(bundle.getFloat(ECG_RESOLUTION_KEY))
                .ecgChannels(bundle.getInt(ECG_CHANNELS_KEY))
                .ecgHighPassFilter(bundle.getFloat(ECG_FILTER_FREQUENCY_KEY))
                .interBeatIntervalEnable(bundle.getBoolean(IBI_ENABLE_KEY))
                .temperatureEnable(bundle.getBoolean(TEMP_ENABLE_KEY))
                .build();

        FarosDeviceManager deviceManager = (FarosDeviceManager) getDeviceManager();
        if (deviceManager != null) {
            deviceManager.applySettings(settings);
        }
    }
}
