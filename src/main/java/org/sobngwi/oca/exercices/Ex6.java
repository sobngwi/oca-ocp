package org.sobngwi.oca.exercices;

import java.util.*;
import java.util.logging.Logger;

/**
 * This example shows how a Java 8 Supplier interface can be used to
 * print a default value if a key is not found in a map.  It also
 * shows how to use the Java 8 Optional class.
 */
public class Ex6 {
    private static final Logger log = Logger.getLogger("Ex6");
    static public void main(String[] argv) {
        // Create a HashMap that associates beings with their
        // personas.
        Map<String, String> beingMap = new HashMap<String, String>() {
            { put("Demon", "Naughty");
              put("Angel", "Nice"); 
            } 
        };

        // The being to search for (who is not in the map).
        String being = "Demigod";

        // Try to find the being in the map (of course, they won't be
        // there).
        Optional<String> angel =
            Optional.ofNullable(beingMap.get("Angel"));
        Optional<String> disposition =
                Optional.ofNullable(beingMap.get(being));

        log.info("disposition of "
                           + being + " = "
                           // Pass a Supplier lambda expression that
                           // returns a default value if the being is
                           // not found.
                           + disposition.orElse("unknown"));
        log.info("disposition of angel : "
                + angel.orElse( "Nothing") );
    }
}

