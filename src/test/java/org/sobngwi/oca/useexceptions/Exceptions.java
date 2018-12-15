package org.sobngwi.oca.useexceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sobngwi.oca.AbstractException;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Exceptions extends AbstractException {

    @Test
    public void method_throwing_runtime_exception_without_catch() {
        String message = "Boum";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(message);

        meth_throwing_a_runtume_exception(message);
    }

    @Test
    public void method_throwing_runtime_exception_with_catch() {
        try {
            meth_throwing_a_runtume_exception("boom");
        } catch (RuntimeException rte) {
            log.fine(String.format("RTE , is thrown  : [%s]", rte.getMessage()));
        }

    }

    @Test
    public void method_throwing_runtime_exception_with_catch_and_finaly() {
        try {
            meth_throwing_a_runtume_exception("boom");
        } catch (RuntimeException rte) {
            log.fine(String.format("RTE , is thrown  %s", rte.getMessage()));
        } finally {
            log.fine(("RTE , is thrown  finally message"));
        }

    }



    @Test
    public void method_throwing_runtime_exception_with_catch_rethrow() {
        String message = "Boum, Boum";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(message);

        try {
            meth_throwing_a_runtume_exception(message);
        } catch (RuntimeException rte) {
            log.fine(String.format("RTE , is rethrown  : [%s]", rte.getMessage()));
            throw new RuntimeException(message);
        }
    }

        @Test
        public void embeddeb_exception() throws FileNotFoundException {
            thrown.expect(FileNotFoundException.class);
            thrown.expectMessage("file not found");

                m2();
                log.fine("A checked exception  is rethrown  :");
        }

    @Test
    public void autoclosableThrowsRTE() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cage Door does not close");

        try (AutoClosableClass autoClosableClass = new AbstractException.AutoClosableClass()) {
            System.out.println();
        }
        catch (IllegalArgumentException e ){
            log.fine(String.format("Caught %s", e.getMessage()));
            throw e;
        }

    }

    @Test
    public void autoclosableNoSuppressedMessage() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Turkeys Ran off");

        try (AutoClosableClass    autoClosableClass = new AbstractException.AutoClosableClass();
                AutoClosableClass autoClosableClass2 = new AbstractException.AutoClosableClass()) {
            throw new RuntimeException( "Turkeys Ran off") ;
        }
        catch (IllegalArgumentException e ){
            log.fine(String.format("Caught %s", e.getMessage()));
            for ( Throwable t : e.getSuppressed()) {
                System.out.println( " Suppressed : " + t.getMessage());
            }
        }

    }

    @Test
    public void autoclosableWithSuppressedMessage() {


        try (AutoClosableClass ignored = new AbstractException.AutoClosableClass();
             AutoClosableClass ignored1 = new AbstractException.AutoClosableClass();
             AutoClosableClass ignored2 = new AbstractException.AutoClosableClass()) {
        }
        catch (IllegalArgumentException e ){
            log.fine(String.format("Caught %s", e.getMessage()));

            assertTrue(e.getSuppressed().length == 2);
            assertThat(2, equalTo(e.getSuppressed().length));
            for ( Throwable t : e.getSuppressed()) {
                log.fine( " Suppressed : " + t.getMessage());
            }
        }

    }

    @Test
    public void autoclosableWithSuppressedMessageAndprimaryException() {


        try (AutoClosableClass    autoClosableClass =  new AbstractException.AutoClosableClass();
             AutoClosableClass    autoClosableClass2 = new AbstractException.AutoClosableClass();
             AutoClosableClass    autoClosableClass3 = new AbstractException.AutoClosableClass();
        ) {
            throw new RuntimeException("rain");
        }
        catch (Exception e ){
            log.finest(String.format("Primary Exception message Caught %s", e.getMessage()));
            assertThat(e.getMessage(), equalTo("rain"));
            assertTrue(e.getSuppressed().length == 3);
            assertThat(3, equalTo(e.getSuppressed().length));
            for ( Throwable t : e.getSuppressed()) {
                log.finest( " Suppressed : " + t.getMessage());
            }
        }

    }

}
