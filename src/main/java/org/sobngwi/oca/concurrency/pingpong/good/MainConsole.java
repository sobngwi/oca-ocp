package org.sobngwi.oca.concurrency.pingpong.good;

/**
 * @class MainConsole
 *
 * @brief This class is the main entry point for a Java console
 *        version of the PingPong application.
 */
public class MainConsole {
    /**
     * The Java virtual machine requires the instantiation of a main
     * method to run the console version of the PlayPingPong app.
     */
    public static void main(String[] args) {
        /** 
         * Initializes the Platform singleton with the appropriate
         * PlatformStrategy, which in this case will be the
         * ConsolePlatform.
         */
        PlatformStrategy.instance
            (new PlatformStrategyFactory
             (System.out).makePlatformStrategy());

        /** Initializes the Options singleton. */
        if ( Options.getOptionsInstance().parseArgs(args))
            System.out.println("Options set ");
        else System.out.println("No Options provide : run with default options");

        /**
         * Create a PlayPingPong object to run the designated number
         * of iterations.
         */
        PlayPingPong pingPong =
            new PlayPingPong(Options.getOptionsInstance().maxIterations(),
                             Options.getOptionsInstance().syncMechanism());

        /**
         * Start a thread to play ping-pong.
         */
        new Thread (pingPong).start();
    }
}
