package org.radarcns.faros;

/**
 * Created by joris on 25/01/2018.
 */

public interface FarosDataDelegate {
    /** less than 10 % */
    int BATTERY_STATUS_CRITICAL = 1;
    int BATTERY_STATUS_LOW = 2;
    int BATTERY_STATUS_MEDIUM = 3;
    int BATTERY_STATUS_FULL = 4;

    void didReceiveAcceleration(double timestamp, float x, float y, float z);
    void didReceiveTemperature(double timestamp, float temperature);
    void didReceiveInterBeatInterval(double timestamp, float interBeatInterval);
    /** ecg signal from the first channel (µV) */
    void didReceiveEcg(double timestamp, float[] channels);
    void didReceiveBatteryStatus(double timestamp, int status);
}
