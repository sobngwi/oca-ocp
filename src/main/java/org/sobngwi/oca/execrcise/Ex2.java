package org.sobngwi.oca.execrcise;

import org.sobngwi.oca.functional.annotation.SelectIterable;

import java.util.*;
import java.util.logging.Logger;

/**
 * This example shows the use of a simple lambda expression in the
 * context of a Java List/ArrayList removeIf() method.
 */
public class Ex2 {
    private static Logger log = Logger.getLogger("Ex2");
    static public void main(String[] argv) {
        List<Integer> list = 
            new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

        log.info(list.toString());
        log.info("Even number in the list ares : " + SelectIterable.selectIf(list, Ex2::even));
        // This lambda expression removes the even numbers from the
        // list.
        list.removeIf(Ex2::even); // static Class:Methode

        log.info(list.toString());
    }

    private static boolean even( int i){
        return  ( i % 2 ) == 0 ;
    }

}

