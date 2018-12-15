package org.sobngwi.oca;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class AbstractException {

    protected  Logger log = Logger.getLogger(AbstractException.class.getName());
    protected static StringBuilder stringBuilder;

    @Before
    public void setUp() throws Exception {
        stringBuilder = new StringBuilder();
    }

    protected void meth_throwing_a_runtume_exception(String message){

        throw new RuntimeException(message) ;
    }

    protected void m1(String message){
        throw new RuntimeException(message) ;
    }

    public void m2() throws FileNotFoundException{
        try {
            m1("m1 call In to m2");
        }
        catch ( Throwable t ){
            throw new FileNotFoundException("file not found");
        }
    }


    @Rule
    public ExpectedException thrown= ExpectedException.none();

    public class AutoClosableClass implements AutoCloseable{

        @Override
        public void close()  throws IllegalArgumentException{
            throw new IllegalArgumentException( "Cage Door does not close");
        }
    }


    protected class SnowStorm {
        class Walk implements AutoCloseable {
            public void close() {
                throw new RuntimeException("snow");
            }
        }
       /* public static void main(String[] args) {
            try (Walk walk1 = new Walk(); Walk walk2 = new Walk();) {
                throw new RuntimeException("rain");
            } catch(Exception e) {
                System.out.println(e.getMessage()
                        + " " + e.getSuppressed().length);
            } } } */
    }
}
