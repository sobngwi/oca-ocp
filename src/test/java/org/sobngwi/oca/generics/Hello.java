package org.sobngwi.oca.generics;

public class Hello <E> {
    private E e;
    public Hello(E e) {
        this.e = e ;
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
