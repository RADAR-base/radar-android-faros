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

import android.os.Parcel;
import android.os.Parcelable;

import org.radarcns.android.device.BaseDeviceState;
import org.radarcns.android.device.DeviceStateCreator;

public class FarosDeviceStatus extends BaseDeviceState {
    private float batteryLevel = Float.NaN;

    private float heartRateVariability = Float.NaN; // or RR-interval

    private float temperature = Float.NaN;

    private float[] acceleration = {Float.NaN, Float.NaN, Float.NaN};

    private float[] ecg = {Float.NaN, Float.NaN, Float.NaN}; // Up to 3 channels

    private int marker = 0;

    public static final Parcelable.Creator<FarosDeviceStatus> CREATOR = new DeviceStateCreator<>(FarosDeviceStatus.class);

    @Override
    public synchronized void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(this.batteryLevel);
        dest.writeFloat(this.heartRateVariability);
        dest.writeFloat(this.temperature);
        dest.writeFloat(this.acceleration[0]);
        dest.writeFloat(this.acceleration[1]);
        dest.writeFloat(this.acceleration[2]);
        dest.writeFloat(this.ecg[0]);
        dest.writeFloat(this.ecg[1]);
        dest.writeFloat(this.ecg[2]);
        dest.writeInt(this.marker);
    }

    public void updateFromParcel(Parcel in) {
        super.updateFromParcel(in);
        batteryLevel = in.readFloat();
        heartRateVariability = in.readFloat();
        temperature = in.readFloat();
        acceleration[0] = in.readFloat();
        acceleration[1] = in.readFloat();
        acceleration[2] = in.readFloat();
        ecg[0] = in.readFloat();
        ecg[1] = in.readFloat();
        ecg[2] = in.readFloat();
        marker = in.readInt();
    }


    /**
     * getter
     */

    public float getBatteryLevel() { return batteryLevel; }

    public float getHeartRateVariability() { return heartRateVariability; }

    @Override
    public boolean hasTemperature() { return true; }
    public float getTemperature() { return temperature; }

    @Override
    public boolean hasAcceleration() {
        return true;
    }
    public float[] getAcceleration() {
        return acceleration;
    }


    /*
     * setters
     */

    public void setBatteryLevel(float level) {
        this.batteryLevel = level;
    }

    public void setHeartRateVariability(float value) {
        this.heartRateVariability = value;
    }

    public void setTemperature(float temp) {
        this.temperature = temp;
    }

    public void setAcceleration(float x, float y, float z) {
        // TODO: is this the correct conversion?
        this.acceleration[0] = x / (float)Math.pow(2, 12);
        this.acceleration[1] = y / (float)Math.pow(2, 12);
        this.acceleration[2] = z / (float)Math.pow(2, 12);
    }
}
