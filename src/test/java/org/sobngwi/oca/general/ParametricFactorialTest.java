package org.sobngwi.oca.general;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ParametricFactorialTest {

    private long number;
    private long expectedResult;
    final Logger log = Logger.getLogger(ParametricFactorialTest.class.getName());

    public ParametricFactorialTest(long number, long expectedResult) {
        this.number = number;
        this.expectedResult= expectedResult;
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { 0, 1 },
                { 1, 1 },
                { 2, 2 },
                { 3, 6 },
                { 4, 24 },
                { 5, 120 },
                { 6, 720 },
                { 7, 5040 },
                { 10, 3628800L },
                { 20, 2432902008176640000L }
        });
    }

    @Test
    public void validateFactorialValues() {
        log.info("Testing : number [" + number + "] expectedResult [" + expectedResult + "]");
        assertThat(Factorial.factorial(number), equalTo(expectedResult) );
    }

}
