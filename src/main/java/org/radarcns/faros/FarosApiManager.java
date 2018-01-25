package org.radarcns.faros;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by joris on 25/01/2018.
 */

public interface FarosApiManager extends Closeable {
    void startScanning() throws IOException;
    void stopScanning() throws IOException;
}
