package org.sobngwi.oca.concurrency.pingpong.good;

import org.sobngwi.oca.functional.Logs.DoLog;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * @class MainConsole
 *
 * @brief This class is the main entry point for a Java console
 *        version of the PingPong application.
 */
public class MainConsole {

    private static final Logger log= DoLog.log();
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
         Options.getOptionsInstance().parseArgs(args);

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
        //new Thread (pingPong).start();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Future<?> result = executorService.submit(pingPong, "NOK");
        while (true){
            try {
               log.info("Pooling ...." + result.get());
                if (!result.get().equals("NOK")) break;
            } catch (ExecutionException e) {
                log.throwing("MainConsole","MainConsole.main" , e);
            } catch (InterruptedException e) {
                log.throwing("MainConsole","MainConsole.main" , e);
            }
        }

        executorService.shutdown();
        System.out.println("End the ping pong Game .");
    }
}
