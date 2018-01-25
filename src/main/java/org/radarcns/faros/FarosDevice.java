package org.radarcns.faros;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by joris on 25/01/2018.
 */

interface FarosDevice extends Closeable {
    int FAROS_180 = 180;
    int FAROS_360 = 360;

    String getName();
    int getType();
    boolean isConnected();
    void connect() throws IOException;

    boolean supportsTemperatureRecording();
    int numberOfSupportedEcgChannels();

    boolean isTemperatureRecording();
    void setTemperatureRecording(boolean doRecord);
    int getEcgSamplingRate();
    void setEcgSamplingRate(int rate);
    boolean isInterBeatIntervalRecording();
    void setInterBeatIntervalRecording(boolean doRecord);
    int getAccelerometerSamplingRate();
    int setAccelerometerSamplingRate(int rate);
    float getAccelerometerResolution();
    void getAccelerometerResolution(float resolution);
    float getBatteryLevel();
}
