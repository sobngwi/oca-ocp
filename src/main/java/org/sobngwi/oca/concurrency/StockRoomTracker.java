package org.sobngwi.oca.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class StockRoomTracker {
    public static void await(CyclicBarrier cb) { // j1
        try { cb.await(); } catch (InterruptedException | BrokenBarrierException e) {
// Handle exception
        }
    }
    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(5,
                () -> System.out.println("Stock Room Full!")); // j2
        IntStream.iterate(1, i -> ++i).limit(25)
                .parallel()
                .peek(x -> System.out.print(x + " "))
                //.forEach(x -> System.out.print(x + " "));
                .forEach(i -> await(cb)); // j3
    }
}
