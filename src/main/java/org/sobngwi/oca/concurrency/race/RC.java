package org.sobngwi.oca.concurrency.race;

import java.math.BigInteger;
import java.util.logging.Logger;
import java.util.stream.LongStream;

public class RC {

    private static Logger log = Logger.getLogger(RC.class.getName());

    public final static BigInteger  getBuggyParalleleFactorial( BigInteger bigInteger) {
        return BuggyFactorial.factorial(bigInteger);
    }
    public final static BigInteger getSynchronizedParallelFactorial (BigInteger bigInteger){
        return SynchronizedParallelFactorial.factorial(bigInteger);
    }

    private static class BuggyFactorial {
        /**
         * This class keeps a running total of the factorial and
         * provides a (buggy) method for multiplying this running
         * total with a value n.
         */
        static class Total {
            /**
             * The running total of the factorial.
             */
            BigInteger mTotal = BigInteger.ONE;

            /**
             * Multiply the running total by @a n.  This method is not
             * synchronized, so it may incur race conditions.
             */
            void multiply(BigInteger n) {
                mTotal = mTotal.multiply(n);
            }
        }

        /**
         * actorial for the given @a n.  There
         * are race conditions wrt a
         * Attempts to return the fccessing shared state, however, so
         * the result may not always be correct.
         */
        static BigInteger factorial(BigInteger n) {
            Total t = new Total();

            LongStream
                    // Create a stream of longs from 1 to n.
                    .rangeClosed(1, n.longValue())

                    // Run the forEach() terminal operation concurrently.
                    .parallel()

                    // Create a BigInteger from the long value.
                    .mapToObj(BigInteger::valueOf)

                    // Multiple the latest value in the range by the
                    // running total (not properly synchronized).
                    .forEach(t::multiply);

            // Return the total, which is also not properly
            // synchronized.
            return t.mTotal;
        }
    }

    /**
     * This class demonstrates how a synchronized statement can avoid
     * race conditions when state is shared between Java threads.
     */
    private static class SynchronizedParallelFactorial {
        /**
         * This class keeps a running total of the factorial and
         * provides a synchronized method for multiplying this running
         * total with a value n.
         */
        static class Total {
            /**
             * The running total of the factorial.
             */
            BigInteger mTotal = BigInteger.ONE;

            /**
             * Multiply the running total by @a n.  This method is
             * synchronized to avoid race conditions.
             */
            void multiply(BigInteger n) {
                synchronized (this) {
                    mTotal = mTotal.multiply(n);
                }
            }

            /**
             * Synchronize get to ensure visibility of the data.
             */
            BigInteger get() {
                synchronized (this) {
                    return mTotal;
                }
            }
        }

        /**
         * Return the factorial for the given @a n using a parallel
         * stream and the forEach() terminal operation.
         */
        static BigInteger factorial(BigInteger n) {
            Total t = new Total();

            LongStream
                    // Create a stream of longs from 1 to n.
                    .rangeClosed(1, n.longValue())

                    // Run the forEach() terminal operation concurrently.
                    .parallel()

                    // Create a BigInteger from the long value.
                    .mapToObj(BigInteger::valueOf)

                    // Multiple the latest value in the range by the
                    // running total (properly synchronized).
                    .forEach(t::multiply);

            // Return the total.
            return t.get();
        }
    }

    public static void main(String[] args) {


        BigInteger fact20 = BuggyFactorial.factorial(BigInteger.valueOf(15));
        log.info("Buggy facotorial of 100 :" + fact20.toString());
         fact20 = SynchronizedParallelFactorial.factorial(BigInteger.valueOf(15));
        log.info("Buggy facotorial of 100 :" + fact20.toString());
    }
}
