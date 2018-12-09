package org.sobngwi.oca.functional.annotation;

import java.util.*;
import java.util.function.Predicate;

public interface OtherPredicates <E> extends Predicate<E>, Iterable<E> {

    static <E>  Predicate<E> not (Predicate<E> p){
        return p.negate();
    }




}
