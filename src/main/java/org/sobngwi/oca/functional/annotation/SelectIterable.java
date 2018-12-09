package org.sobngwi.oca.functional.annotation;

import java.util.*;
import java.util.function.Predicate;

public interface SelectIterable<E> extends Iterable<E>{

     static <E> List<E> selectIf(Iterable<E> iterable, Predicate<? super E> filter) {
        Objects.requireNonNull(filter);

        List<E> result = new ArrayList<>();

        boolean selected = false;
        final Iterator<E> each = iterable.iterator();
        while (each.hasNext()) {
            E e= each.next();
            if (filter.test(e)) {
                result.add(e);
                selected = true;
            }
        }

        if ( ! selected) return result;
        return Collections.unmodifiableList(result);
    }
}
