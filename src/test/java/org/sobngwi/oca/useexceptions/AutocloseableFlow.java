package org.sobngwi.oca.useexceptions;

import org.junit.Test;
import org.sobngwi.oca.AbstractException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class AutocloseableFlow extends AbstractException {

   static class Door implements AutoCloseable{

       @Override
       public void close() throws Exception {
           System.out.println("Throw D");
           stringBuilder.append("D");
       }
   }
    static class Window implements AutoCloseable{

        @Override
        public void close() throws Exception {
            stringBuilder.append("W");
            System.out.println("Throw W");
            throw new RuntimeException("W");
        }
    }

    @Test
    public void autocloseableFlowExeptionThrows() throws Throwable{
        String message = "W";
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(message);

    try (Door d = new Door(); Window w = new Window()) {
        stringBuilder.append("T");
        }

    fail();

    }

    @Test
    public void autocloseableFlows() throws Throwable{
        try (Door d = new Door(); Window w = new Window()) {
            stringBuilder.append("T");
        }
        catch (Exception e){
            System.out.println("catch E ");
            stringBuilder.append("E");
        }
    finally {
        stringBuilder.append("F");
    }

        assertThat(stringBuilder.toString(), equalTo("TWDEF"));
    }
}
