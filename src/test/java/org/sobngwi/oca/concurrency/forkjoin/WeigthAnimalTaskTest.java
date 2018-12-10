package org.sobngwi.oca.concurrency.forkjoin;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WeigthAnimalTaskTest {

    private Double[] weigths;
    private ForkJoinTask<Double> task;
    private ForkJoinPool  pool;

    @Before
    public void setUp() throws Exception {
        weigths = new Double[10];
        task = new WeigthAnimalTask(0,weigths.length, weigths);
        pool = new ForkJoinPool();
    }

    @Test
    public void shouldComputeTheSumUsingRecusiveTasksONthreadJoinPool() {

        Double sum =  pool.invoke(task);
        Double verification = Stream.of(weigths)
                .mapToDouble(w -> w.doubleValue())
                .sum();

        assertEquals(sum, verification, 0.00001);
    }
}