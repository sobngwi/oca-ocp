package org.sobngwi.oca.functional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolSupplier {
    private static final int MAX_THREADS = 10;
    private static volatile ExecutorService result = null ;// Executors.newFixedThreadPool(MAX_THREADS)

    public static synchronized ExecutorService getThreadPoolInstance() {
        if (result == null) {
           synchronized (ThreadPoolSupplier.class) {
               result = Executors.newFixedThreadPool(MAX_THREADS);
               return result;
           }
        }
        else {
            return result;
        }
    }
}
