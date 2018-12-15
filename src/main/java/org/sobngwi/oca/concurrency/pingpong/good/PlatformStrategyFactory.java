package org.sobngwi.oca.concurrency.pingpong.good;

import java.util.HashMap;

import static org.sobngwi.oca.concurrency.pingpong.good.PlatformStrategyFactory.PlatformType.ANDROID;
import static org.sobngwi.oca.concurrency.pingpong.good.PlatformStrategyFactory.PlatformType.PLAIN_JAVA;

/**
 * @class PlatformStrategyFactory
 * 
 * @brief This class is a Factory that uses the Command pattern to
 *        create the designated @a PlatformStrategy implementation
 *        (e.g., either an Android application or Java console
 *        application) at runtime.  The class plays the role of the
 *        Creator in the Factory Method pattern.  It also uses the
 *        Command pattern internally to efficiently create the
 *        appropriate type of @a PlatformStrategy subclass object.
 */
public class PlatformStrategyFactory {
    /**
     * Enumeration distinguishing platforms Android from plain ol' Java.
     */
    public enum PlatformType {
        ANDROID,
        PLAIN_JAVA
    };
    
    /**
     * Keep track of the type of platform.  This value won't change at
     * runtime.
     */
    private final PlatformType mPlatformType =
        System.getProperty("java.specification.vendor").indexOf("Android") >= 0
            ? ANDROID
            : PLAIN_JAVA;

    /**
     * This interface uses the Command pattern to create @a
     * PlatformStrategy implementations at runtime.
     */
    private interface IPlatformStrategyFactoryCommand {
         PlatformStrategy execute();
    }

    /**
     * HashMap used to associate the PlatformType with the
     * corresponding command object whose execute() method creates the
     * appropriate type of @a PlatformStrategy subclass object.
     */
    private HashMap<PlatformType, IPlatformStrategyFactoryCommand> mPlatformStrategyMap =
        new HashMap<PlatformType, IPlatformStrategyFactoryCommand>();

    /**
     * Constructor stores the objects that perform output and
     * synchronization for a particular Java platform, such as
     * PlatformStrategyConsole or PlatformStrategyAndroid.
     */
    public PlatformStrategyFactory(final Object output) {
        if (mPlatformType == ANDROID)
            /**
             * Map the PlatformType of ANDROID to a command object that
             * creates an @a PlatformStrategyAndroid implementation.
             */
            // Creates the PlatformStrategyAndroid.
            mPlatformStrategyMap.put(ANDROID,
                    () -> new PlatformStrategyAndroid(output));
        else // Creates the PlatformStrategyConsole.
            mPlatformStrategyMap.put(PLAIN_JAVA,
                    () -> new PlatformStrategyConsole(output));
    }

    /**
     * Factory method that creates and returns a new @a
     * PlatformStrategy object based on underlying Java platform.
     */
    public PlatformStrategy makePlatformStrategy() {
        return mPlatformStrategyMap.get(mPlatformType).execute();
    }
}
