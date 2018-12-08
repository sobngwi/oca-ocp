package org.sobngwi.oca.concurrency.race;

import org.junit.Test;

import java.math.BigInteger;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class RCTest {

    private static Logger log = Logger.getLogger(RCTest.class.getName());

    @Test ( expected = RuntimeException.class)
    public void raceCondition_computeFactorialNOkForSmallNumbers() {


        for ( long   count = 1 ; count < Integer.MAX_VALUE ; count++ ){
             BigInteger buggy = RC.getBuggyParalleleFactorial(BigInteger.valueOf(count));
             BigInteger synchronised = RC.getSynchronizedParallelFactorial(BigInteger.valueOf(count));
            if (  buggy.compareTo(synchronised) != 0 )
            {
                log.info(String.format("raceCondition: Iteration [%d] : buggy :[%s] != synchronised [%s]", count, buggy, synchronised));
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
                log.info(String.format("raceCondition: Iteration [%d] : firstValue, [%s] != secondValue [%s]", count, firstValue, secondValue));
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
}