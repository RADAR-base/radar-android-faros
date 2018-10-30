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

import android.os.Parcel;
import android.os.Parcelable;

import org.radarcns.android.device.BaseDeviceState;
import org.radarcns.android.device.DeviceStateCreator;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class FarosDeviceStatus extends BaseDeviceState {
    private float batteryLevel = Float.NaN;

    private float heartRate = Float.NaN; // or RR-interval

    private float temperature = Float.NaN;

    private float[] acceleration = {Float.NaN, Float.NaN, Float.NaN};

    public static final Parcelable.Creator<FarosDeviceStatus> CREATOR = new DeviceStateCreator<>(FarosDeviceStatus.class);

    @Override
    public synchronized void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(this.batteryLevel);
        dest.writeFloat(this.heartRate);
        dest.writeFloat(this.temperature);
        dest.writeFloat(this.acceleration[0]);
        dest.writeFloat(this.acceleration[1]);
        dest.writeFloat(this.acceleration[2]);
    }

    public void updateFromParcel(Parcel in) {
        super.updateFromParcel(in);
        batteryLevel = in.readFloat();
        heartRate = in.readFloat();
        temperature = in.readFloat();
        acceleration[0] = in.readFloat();
        acceleration[1] = in.readFloat();
        acceleration[2] = in.readFloat();
    }

    @Override
    public synchronized float getBatteryLevel() { return batteryLevel; }

    public synchronized void setBatteryLevel(float level) {
        this.batteryLevel = level;
    }

    @Override
    public boolean hasTemperature() { return true; }

    @Override
    public synchronized float getTemperature() {
        return this.temperature;
    }

    public synchronized void setTemperature(float temp) {
        this.temperature = temp;
    }

    @Override
    public boolean hasAcceleration() {
        return true;
    }

    @Override
    public synchronized float[] getAcceleration() {
        return acceleration;
    }

    public synchronized void setAcceleration(float x, float y, float z) {
        this.acceleration[0] = x;
        this.acceleration[1] = y;
        this.acceleration[2] = z;
    }

    @Override
    public boolean hasHeartRate() {
        return true;
    }

    @Override
    public synchronized float getHeartRate() {
        return heartRate;
    }

    public synchronized void setHeartRate(float value) {
        this.heartRate = value;
    }

    @Override
    public String toString() {
        return "FarosDeviceStatus{" +
                "batteryLevel=" + batteryLevel +
                ", heartRate=" + heartRate +
                ", temperature=" + temperature +
                ", acceleration=" + Arrays.toString(acceleration) +
                '}';
    }
}
