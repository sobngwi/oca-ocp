package org.sobngwi.oca.concurrency.pingpong.good;

import org.sobngwi.oca.functional.Logs.DoLog;

import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * @class Options
 * @brief This class implements the Singleton pattern to handle
 * command-line option processing.
 */
public class Options {
    private static final Logger log = DoLog.log();
    /**
     * The singleton @a Options getOptionsInstance.
     */
    private static Options mUniqueInstance = null;

    /**
     * Maximum number of iterations to run the program (defaults to
     * 10).
     */
    private int mMaxIterations = 10;

    /**
     * Which synchronization to use, e.g., "SEMA", "COND", "MONOBJ",
     * and "QUEUE".  Defaults to "SEMA".
     */
    private SynchroMechanism mSyncMechanism = SynchroMechanism.SEMA;

    /**
     * Method to return the one and only singleton uniqueInstance.
     */
    public static Options getOptionsInstance() {
        if (mUniqueInstance == null)
            mUniqueInstance = new Options();

        return mUniqueInstance;
    }

    /**
     * Number of iterations to run the program.
     */
    public int maxIterations() {
        return mMaxIterations;
    }

    /**
     * Which synchronization to use, e.g., "SEMA", "COND", "MONOBJ",
     * "QUEUE", and "PADDLE".  Defaults to "PADDLE".
     */
    public SynchroMechanism syncMechanism() {
        return mSyncMechanism;
    }

    /**
     * Parse command-line arguments and set the appropriate values.
     */
    public boolean parseArgs(String argv[]) {
        if (argv != null) {
            Stream.of(argv).forEachOrdered(o -> log.info("Main Arg: " + o ));
            for (int argc = 0; argc < argv.length; argc += 2)
                if (argv[argc].equals("-i"))
                    mMaxIterations = Integer.parseInt(argv[argc + 1]);
                else if (argv[argc].equals("-s"))
                    switch (argv[argc + 1]) {
                        case "1":
                            mSyncMechanism = SynchroMechanism.SEMA;
                            break;
                        case "2":
                            mSyncMechanism = SynchroMechanism.MONOBJ;
                            break;
                        case "3":
                            mSyncMechanism = SynchroMechanism.COND;
                            break;
                        case "4":
                            mSyncMechanism = SynchroMechanism.QUEUE;
                            break;

                        default:
                            mSyncMechanism = SynchroMechanism.SEMA;
                            break;

                    }
                else {
                    printUsage();
                    return false;
                }
            log.info(String.format("Running with options mMaxIterations =[%d], mSyncMechanism =[%s]",
                    mMaxIterations, mSyncMechanism));
            return true;
        } else
            return false;
    }

    /**
     * Print out usage and default values.
     */
    public void printUsage() {
        PlatformStrategy platform = PlatformStrategy.instance();
        platform.errorLog("Options",
                "\nHelp Invoked on ");
        platform.errorLog("Options",
                "[-his] ");
        platform.errorLog("", "");
        platform.errorLog("", "");

        platform.errorLog("Options",
                "Usage: ");
        platform.errorLog("Options",
                "-h: invoke help");
        platform.errorLog("Options",
                "-i max-number-of-iterations");
        platform.errorLog("Options",
                "-s sync-mechanism (e.g., \"SEMA\", \"COND\", \"MONOBJ\", or \"QUEUE\"");
    }

    /**
     * Make the constructor private for a singleton.
     */
    private Options() {
    }
}
