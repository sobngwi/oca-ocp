package org.sobngwi.oca.functional.collect;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

import static org.junit.Assert.*;

public class AveregerTest {

    @Test
    public void computeTheAvegrage() {

      Avereger result =   DoubleStream
                .generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, Math.PI))
                .limit(1000)
                .collect(Avereger.averegerSupplier,Avereger.accumulator, Avereger.combiner);
        double avg = Math.abs(result.get());

        assertEquals
                (0.05, avg, 0.150000000 ) ;
    }
}