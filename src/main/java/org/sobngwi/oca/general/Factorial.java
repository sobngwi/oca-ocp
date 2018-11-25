package org.sobngwi.oca.general;

public class Factorial {

    public static  long factorial(long number) {
        if ( number < 0 ) {
            throw new IllegalArgumentException("The number should be positive.");
        }
        else if (number == 0) {
            return 1;
        }
        else
            return number*factorial(number - 1);
    }

}
