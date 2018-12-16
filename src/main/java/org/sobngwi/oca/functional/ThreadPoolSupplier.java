package org.sobngwi.oca.functional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolSupplier {
    private static final int MAX_THREADS = 10;
    private static volatile ExecutorService result = null;

    public static synchronized ExecutorService getThreadPoolInstance() {

        if (result == null) return Executors.newFixedThreadPool(MAX_THREADS);
        else {
            result = Executors.newFixedThreadPool(MAX_THREADS);
            return result;
        }
    }
}
