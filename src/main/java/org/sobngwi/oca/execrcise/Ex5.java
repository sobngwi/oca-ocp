package org.sobngwi.oca.execrcise;

import java.util.*;
import java.util.logging.Logger;

/**
 * This example shows how a Java 8 Consumer interface can be used with
 * forEach() to print out the values in a list by binding the
 * System.out println() method to the forEach() Consumer parameter.
 */

public class Ex5 {
    private static final Logger log = Logger.getLogger("Ex5");
    static public void main(String[] argv) {
        // Create a list of threads.
        List<Thread> threads =
            Arrays.asList(new Thread("Larry"),
                          new Thread("Curly"),
                          new Thread("Moe"));

        // forEach() takes a Consumer, which is bound to the
        // System.out println() method.
        threads.forEach(System.out::println);

        // Sort the threads by their names.
        threads.sort(Comparator.comparing(Thread::getName));

        // forEach() takes a Consumer, which is bound to the
        // System.out println() method.
        threads.forEach( t -> log.info(t::toString));
    }
}

