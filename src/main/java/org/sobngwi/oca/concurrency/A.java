package org.sobngwi.oca.concurrency;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class A {


    public static void main(String[] args) {
        new Callable<String>() {

            @Override
            public String call() throws Exception { // V call() throws Exception;
                return null;
            }
        };

        new Runnable() {

            @Override
            public void run() { // public abstract void run();

            }
        };

        Arrays.asList("Alain", "Narcisse")
                .parallelStream()
                .parallel()
                .reduce((c1, c2) -> c1.length() + c2.length() + "");

    }

}
