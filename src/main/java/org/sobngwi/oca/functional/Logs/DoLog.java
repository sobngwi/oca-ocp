package org.sobngwi.oca.functional.Logs;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class DoLog {

    private static volatile  Logger log = null;
    public synchronized static Logger log() {

        InputStream stream = DoLog.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            if (log == null)
            return Logger.getLogger(DoLog.class.getName());
            else {
                log = Logger.getLogger(DoLog.class.getName());
                return log ;
            }
        }
        catch (IOException e) {
            return Logger.getLogger(DoLog.class.getName());
        }
    }
}
