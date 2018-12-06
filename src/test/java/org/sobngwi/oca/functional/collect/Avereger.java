package org.sobngwi.oca.functional.collect;

import java.util.function.BiConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;

public class Avereger {
    private double sum = 0 ;
    private  long count = 0;

    public static final  Supplier<Avereger> averegerSupplier = Avereger::new ;//() -> new Avereger();
    public static final  ObjDoubleConsumer<Avereger>  accumulator = (b, i) -> b.include(i);
    public static final  BiConsumer<Avereger, Avereger> combiner = ( c1 , c2 ) -> c1.merge(c2);

    public Avereger() {}

    private void include( Double other){
        count += 1 ;
        sum += other;
    }

    private void merge( Avereger other){
        count += other.count ;
        sum += other.sum;
    }

    public Double get(){
        return sum/count;
    }



}
