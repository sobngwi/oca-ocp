package org.sobngwi.oca.functional.Logs;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public interface DoLog {
    static Logger log() {

        InputStream stream = DoLog.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            return Logger.getLogger(DoLog.class.getName());

        } catch (IOException e) {
            return Logger.getLogger(DoLog.class.getName());
        }
    }
}
