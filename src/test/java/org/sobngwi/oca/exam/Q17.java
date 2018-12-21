package org.sobngwi.oca.exam;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Q17{
    private static AtomicInteger monkey1 = new AtomicInteger(0); // m1
    private static AtomicLong monkey2 = new AtomicLong(0);

    public static void main(String[] args) {
        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor(); // m2

            for(int i=0; i<100; i++)
                service.submit(() -> monkey1.getAndIncrement()); // m3
            for(int i=0; i<100; i++)
                service.submit(() -> monkey2.incrementAndGet());
            System.out.println(monkey1+" "+monkey2); // m4
        } finally {
            if(service != null) service.shutdown();
        }
    }
}
