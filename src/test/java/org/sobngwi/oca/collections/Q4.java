package org.sobngwi.oca.collections;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

public class Q4 {
    class A {}

    class B extends A {}

    class C extends B {}



    class D<C> {

// INSERT CODE HERE

     //  C c1 = new C();
    }

    public static void main(String[] args) {
        Set<String> s = new HashSet<>();
        s.add("lion");
        s.add("tiger");
        s.add("bear");
       // s.forEach(s -> System.out.println(s));

         ArrayDeque<String> greetings = new ArrayDeque<String>();
         greetings.push("hello");
         greetings.push("hi");
         greetings.push("ola");
         greetings.pop();
         greetings.peek();

         while (greetings.peek() != null)

             System.out.print("DEQUE PUSH : LIFO  " + greetings.pop());
        System.out.println();

        greetings.clear();;
        greetings.offer("hello");
        greetings.offer("hi");
        greetings.offer("ola");
        greetings.pop();
        greetings.peek();

        while (greetings.peek() != null)

            System.out.print("DEQUEU OFFER -> FIFO " + greetings.pop());
        System.out.println();
    }

}
