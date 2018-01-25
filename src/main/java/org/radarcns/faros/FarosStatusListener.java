package org.radarcns.faros;

/**
 * Created by joris on 25/01/2018.
 */

public interface FarosStatusListener {
    int DISCONNECTED = 1;
    int CONNECTED = 2;
    int CONNECTING = 3;
    int DISCONNECTING = 4;
    int READY = 5;

    void onStatusUpdate(int status);
    void onDeviceScanned(FarosDevice device);
}
