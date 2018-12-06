package org.sobngwi.oca.functional.pure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {

    private final Iterable<E> self;

    public SuperIterable(Iterable<E> self) {
        this.self = self;
    }

    @Override
    public Iterator<E> iterator() {
        return self.iterator();
    }

    public SuperIterable<E> filter(Predicate<E> pred) {
        List<E> results = new ArrayList<>();
        for (E e : self) {
            if (pred.test(e)) {
                results.add(e);
            }
        }
        return new SuperIterable<>(results);
    }

    public SuperIterable<E> filterWithForEvery(Predicate<E> pred) {
        List<E> results = new ArrayList<>();

        this.forEvery(s -> { if ( pred.test(s)) results.add(s) ;});

           return new SuperIterable<>(results);
        }

    public <F> SuperIterable<F> map(Function<E, F> operator) {
        List<F> results = new ArrayList<>();

        this.forEvery(e ->  results.add( operator.apply(e)));

        return new SuperIterable<>(results);
    }

    public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> operator ){
        List<F> results = new ArrayList<>();

        this.forEvery(e -> operator.apply(e).forEvery(f -> results.add(f)));

        return  new SuperIterable<>(results);
    }


    public void forEvery(Consumer<E> cons) {
        for (E e : self )
            cons.accept(e);
    }

    @Override
    public String toString() {
        return self.toString();
    }


    public static void main(String[] args) {
        SuperIterable<String> strings = new SuperIterable<>(Arrays.asList("LigthCoral", "pink", "Orange",
                "plum", "Blue", "limergen"));
        System.out.println(strings);

        strings.forEvery( s -> System.out.print(s + " "));
    }
}
