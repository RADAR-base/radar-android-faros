package org.radarcns.faros;

import android.os.Handler;

/**
 * Created by joris on 25/01/2018.
 */

public class FarosApiFactory {
    public FarosApiManager createApiManager(FarosStatusListener statusListener, FarosDataDelegate dataListener, Handler handler) {
        return new FarosConnectedThread(statusListener, dataListener, handler);
    }
}
