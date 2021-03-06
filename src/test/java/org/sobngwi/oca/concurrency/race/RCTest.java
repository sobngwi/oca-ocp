package org.sobngwi.oca.concurrency.race;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class RCTest {

    private static Logger log = Logger.getLogger(RCTest.class.getName());
    private int sheepCount1;
    AtomicLong sheepCount2;

    @Before
    public void setUp(){
         sheepCount1 = 0 ;
         sheepCount2= new AtomicLong(0);
    }

    @Test ( expected = RuntimeException.class)
    public void raceCondition_computeFactorialNOkForSmallNumbers() {


        for ( long   count = 1 ; count < Integer.MAX_VALUE ; count++ ){
             BigInteger buggy = RC.getBuggyParalleleFactorial(BigInteger.valueOf(count));
             BigInteger synchronised = RC.getSynchronizedParallelFactorial(BigInteger.valueOf(count));
            if (  buggy.compareTo(synchronised) != 0 )
            {
                log.finest(String.format("raceCondition: Iteration [%d] : buggy :[%s] != synchronised [%s]", count, buggy, synchronised));
                assertThat("buggy =" + buggy.toString() + " synchronised=" + synchronised + " .", buggy, not(equalTo(synchronised)));
                throw new RuntimeException(String.format("buggy :[%s] != synchronised [%s]", buggy, synchronised));
            }
        }
        fail();
    }

    @Test(expected = RuntimeException.class)
    public void raceCondition_computeFactorialNOKForSmallNumbersOnBuggy() {


        for ( long   count = 1 ; count < Integer.MAX_VALUE ; count++ ){
            BigInteger firstValue =  RC.getBuggyParalleleFactorial(BigInteger.valueOf(count));
            BigInteger secondValue = RC.getBuggyParalleleFactorial(BigInteger.valueOf(count));
            if (  firstValue.compareTo(secondValue) != 0 )
            {
                log.finest(String.format("raceCondition: Iteration [%d] : firstValue, [%s] != secondValue [%s]", count, firstValue, secondValue));
                assertThat("firstValue =" + firstValue.toString() + " secondValue=" + secondValue + " .", firstValue, not(equalTo(secondValue)));
                throw new RuntimeException(String.format("firstValue :[%s] != secondValue [%s]", firstValue, secondValue));
            }
        }
        fail();
    }

    @Test
    public void NoRaceCondition_computeFactorialOKForNumbersOnSynchronizedMethod() {


        for ( long   count = 1 ; count < 1000 ; count++ ){
            BigInteger firstValue =  RC.getSynchronizedParallelFactorial(BigInteger.valueOf(count));
            BigInteger secondValue = RC.getSynchronizedParallelFactorial(BigInteger.valueOf(count));
            if (  firstValue.compareTo(secondValue) != 0 )
            {
                System.out.println(String.format(" Iteration [%d] : firstValue, [%s] != secondValue [%s]", count, firstValue, secondValue));
                assertThat("firstValue =" + firstValue.toString() + " secondValue=" + secondValue + " .", firstValue, equalTo(secondValue));
                throw new RuntimeException(String.format("firstValue :[%s] != secondValue [%s]", firstValue, secondValue));
            }
        }
    }

    @Test
    public void NoRaceCondition_computeFactorialOKForNumbersOnSynchronizedMethodReduceTerminalOperation() {

        for ( long   count = 1 ; count < 1000 ; count++ ){
            BigInteger firstValue =  RC.getFactorialReduce(BigInteger.valueOf(count));
            BigInteger secondValue = RC.getFactorialReduce(BigInteger.valueOf(count));
            if (  firstValue.compareTo(secondValue) != 0 )
            {
                System.out.println(String.format(" Iteration [%d] : firstValue, [%s] != secondValue [%s]", count, firstValue, secondValue));
                assertThat("firstValue =" + firstValue.toString() + " secondValue=" + secondValue + " .", firstValue, equalTo(secondValue));
                throw new RuntimeException(String.format("firstValue :[%s] != secondValue [%s]", firstValue, secondValue));
            }
        }
    }

    @Test
    public void rc_on_non_atomic_variables() {
        AtomicLong atomicLong = new AtomicLong(0);
        final long[] longs ={0};
        IntStream.iterate(1, i->1)
                .parallel()
                .limit(10000)
                .forEach(i -> atomicLong.incrementAndGet());
        IntStream.iterate(1, i->1)
                .parallel()
                .limit(10000)
                .forEach(i -> ++longs[0]);
        assertThat(atomicLong.toString(), equalTo("10000"));
        assertThat(atomicLong.toString(), not(equalTo(longs[0])));

    }


    @Test
    public void noRace_ConditionWithThreadPollFixedTo_One() throws  InterruptedException{
        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(1);
            for ( int i = 1; i< 1000000 ; i ++)
                executorService.execute(() -> {
                    sheepCount2.incrementAndGet();
                    sheepCount1++; });
            sleep(100);

        }
        finally {
            if ( executorService != null) executorService.shutdown();
        }
        assertTrue(sheepCount1 == sheepCount2.intValue());


    }

    @Test
    public void race_ConditionWithThreadPollMoreThan_One() throws  InterruptedException{
        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(5);
            for ( int i = 1; i< 1000000 ; i ++)
                executorService.execute(() -> {
                    sheepCount2.incrementAndGet();
                    sheepCount1++; });
            sleep(100);

        }
        finally {
            if ( executorService != null) executorService.shutdown();
        }
        assertTrue(sheepCount1 != sheepCount2.intValue());


    }
}