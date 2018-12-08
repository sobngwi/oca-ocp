package org.sobngwi.oca.functional.annotation;

import java.util.function.Predicate;

@FunctionalInterface
public interface OtherPredicates <T> extends Predicate<T>  {

    static <T>  Predicate<T> not (Predicate<T> p){
        return p.negate();
    }

}
