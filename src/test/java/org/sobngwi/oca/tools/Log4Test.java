package org.sobngwi.oca.tools;

import org.sobngwi.oca.general.ParametricFactorialTest;

import java.util.Arrays;
import java.util.logging.Logger;

public class  Log4Test {

    final Logger log = Logger.getLogger(Log4Test.class.getName());

    private enum Level{
        INFO,DEBUG,ERROR
    }

    public static void log( final String classNameAndMethodName, final String... theLogMessage){

        log(Level.DEBUG, classNameAndMethodName + "-" + String.join(", ", theLogMessage)) ;
    }

    public static void log(Level level, String theLogMessage) {

        switch (level) {
            case INFO:
                break;
            case DEBUG:
                System.out.println(theLogMessage);
                break;
            case ERROR:
                System.err.println(theLogMessage);
                break;
            default:
                throw new IllegalArgumentException("Unknown level");
        }
    }

}
