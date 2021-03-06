package org.sobngwi.oca.concurrency.pingpong.good;

import org.sobngwi.oca.functional.Logs.DoLog;
import org.sobngwi.oca.functional.ThreadPoolSupplier;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * @class PlayPingPong
 * @brief This class implements a Java program that creates two
 * threads, Ping and Pong, to alternately print "Ping" and
 * "Pong", respectively, on the display. It uses the Template
 * Method, Strategy, and Factory Method patterns to factor out
 * common code, enhance portability, and simplify the program
 * design.
 */
public class PlayPingPong implements Runnable {

    private static final Logger log = DoLog.log();
    /**
     * Number of iterations to ping/pong.
     */
    private final int mMaxIterations;

    /**
     * Which synchronization to use, e.g., "SEMA", "COND", "MONOBJ",
     * "QUEUE".
     */
    private final SynchroMechanismType synchroMechanismType;

    /**
     * Maximum number of ping pong Threads.
     */
    private final static int MAX_PING_PONG_THREADS = 2;

    /**
     * Constants used to distinguish between ping and pong threads.
     */
    private final static int PING_THREAD = 0;
    private final static int PONG_THREAD = 1;

    /**
     * Constructor initializes the data members.
     */
    public PlayPingPong(int maxIterations,
                        SynchroMechanismType syncMechanism) {
        // Number of iterations to perform pings and pongs.
        mMaxIterations = maxIterations;

        // Which type of synchronization mechanism to use.
        synchroMechanismType = syncMechanism;
    }

    /**
     * Start running the ping/pong code, which can be called from a
     * main() method in a Java class, an Android Activity, etc.
     */
    public void run() {
        // Indicate a new game is beginning.
        PlatformStrategy.instance().begin();

        // Let the user know we're starting.
        log.info("PlayPingPong : Ready...Set...Go!");
        //PlatformStrategy.instance().print("Ready...Set...Go!");

        // Create the ping and pong threads. 
        PingPongThread pingPongThreads[] =
                new PingPongThread[MAX_PING_PONG_THREADS];

        // Create the appropriate type of threads with the designated
        // synchronization mechanism.
        makePingPongThreads(synchroMechanismType, pingPongThreads);

        // Start ping and pong threads, which calls their run()
        // methods.
        /*for (PingPongThread thread : pingPongThreads)
            thread.start();*/
        final ExecutorService threadPoolExecutor = ThreadPoolSupplier.getThreadPoolInstance();

        Future<String> pong = threadPoolExecutor.submit(pingPongThreads[PONG_THREAD], "OK");
        Future<String> ping = threadPoolExecutor.submit(pingPongThreads[PING_THREAD], "OK");


        // Barrier synchronization to wait for all work to be done
        // before exiting play().
        PlatformStrategy.instance().awaitDone();
        try {
            if (ping.get().equals("OK") && pong.get().equals("OK")) {
                log.finest("PlayPingPong.run : ping and pong operations commands done.");
               // threadPoolExecutor.shutdown();
               // if (!threadPoolExecutor.awaitTermination(1, TimeUnit.NANOSECONDS)) threadPoolExecutor.shutdownNow();
                log.info("PlayPingPong.run: pool shutdown ... is terminated =" + threadPoolExecutor.isTerminated());
            }
        } catch (ExecutionException | InterruptedException e) {
            log.throwing(PlayPingPong.class.getName(), "run", e);
        }
        // Let the user know we're done.
        log.info("PlayPingPong : Done");
        //PlatformStrategy.instance().print("Done!");
    }

    /**
     * Factory method that creates the designated instances of the
     * PingPongThread subclass based on the @code syncMechanism
     * parameter.
     */
    private void makePingPongThreads(SynchroMechanismType synchroMechanismType,
                                     PingPongThread[] pingPongThreads) {
        switch (synchroMechanismType) {
            case SEMA:
                // Create the Java Semaphores that ensure threads print
                // "ping" and "pong" in the correct alternating order.
                Semaphore pingSema =
                        new Semaphore(1); // Starts out unlocked.
                Semaphore pongSema =
                        new Semaphore(0);

                pingPongThreads[PING_THREAD] =
                        new PingPongThreadSema("ping",
                                pingSema,
                                pongSema,
                                mMaxIterations);
                pingPongThreads[PONG_THREAD] =
                        new PingPongThreadSema("pong",
                                pongSema,
                                pingSema,
                                mMaxIterations);
                        break;
            case MONOBJ:
                // Create the BinarySemaphores (implemented as Java
                // build-in monitor objects) that ensure threads print
                // "ping" and "pong" in the correct alternating order.
                PingPongThreadMonObj.BinarySemaphore pingBiSema =
                        new PingPongThreadMonObj.BinarySemaphore(true); // Start out unlocked.
                PingPongThreadMonObj.BinarySemaphore pongBiSema =
                        new PingPongThreadMonObj.BinarySemaphore(false);

                pingPongThreads[PING_THREAD] =
                        new PingPongThreadMonObj("ping",
                                pingBiSema,
                                pongBiSema,
                                mMaxIterations);
                pingPongThreads[PONG_THREAD] =
                        new PingPongThreadMonObj("pong",
                                pongBiSema,
                                pingBiSema,
                                mMaxIterations);
                break;
            case COND:
                // Create the ReentrantLock and Conditions that ensure
                // threads print "ping" and "pong" in the correct
                // alternating order.
                ReentrantLock lock = new ReentrantLock();
                Condition pingCond = lock.newCondition();
                Condition pongCond = lock.newCondition();

                PingPongThreadCond pingThread =
                        new PingPongThreadCond("ping",
                                lock,
                                pingCond,
                                pongCond,
                                true,
                                mMaxIterations);
                PingPongThreadCond pongThread =
                        new PingPongThreadCond("pong",
                                lock,
                                pongCond,
                                pingCond,
                                false,
                                mMaxIterations);
                // Exchange Thread IDs.
                pingThread.setOtherThreadId(pongThread.getId());
                pongThread.setOtherThreadId(pingThread.getId());

                pingPongThreads[PING_THREAD] = pingThread;
                pingPongThreads[PONG_THREAD] = pongThread;
                break;

            case QUEUE:
                // Create the LinkedBlockingQueues that ensure threads
                // print "ping" and "pong" in the correct alternating
                // order.
                LinkedBlockingQueue<Object> pingQueue =
                        new LinkedBlockingQueue<Object>();
                LinkedBlockingQueue<Object> pongQueue =
                        new LinkedBlockingQueue<Object>();
                Object pingPongBall = new Object();

                try {
                    // Initialize this implementation by putting an object
                    // onto the "pong" queue.
                    pongQueue.put(pingPongBall);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }

                pingPongThreads[PING_THREAD] =
                        new PingPongThreadBlockingQueue("ping",
                                pingQueue,
                                pongQueue,
                                mMaxIterations);
                pingPongThreads[PONG_THREAD] =
                        new PingPongThreadBlockingQueue("pong",
                                pongQueue,
                                pingQueue,
                                mMaxIterations);
                break;
            default:
                throw new RuntimeException("Not Yet implemented !");
        }
    }
}

