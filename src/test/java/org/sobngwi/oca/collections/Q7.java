package org.sobngwi.oca.collections;

import java.util.HashSet;
import java.util.Set;

public class Q7 {
 static abstract  class A{}
 static class B extends A{}
 static class C extends B{}

    public static void main(String[] args) {
     Set<A> as = new HashSet<>();
     Set<A> a = new HashSet<>();
        Set<B> b = new HashSet<>();
        Set<C> c = new HashSet<>();
        as.add(new B());
        as.add(new C());

        add(a);
        //add(b);
        //add(c);
    }

    static void add(Set<A> set){
     set.forEach(System.out::println);
    }
}
