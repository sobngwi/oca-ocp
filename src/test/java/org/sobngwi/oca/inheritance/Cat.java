package org.sobngwi.oca.inheritance;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Cat extends Animal {

    //@Override
    private void m(){
    };

    public static void main(String[] args) {

        StringBuilder s1 = new StringBuilder("H");
        StringBuilder s2 = new StringBuilder("H");
        System.out.println(s1.equals(s2));
        System.out.println(s1 == s2);

        List<String> arraysList= Arrays.asList("Hello");
        List<String> linkedList= new LinkedList<>();
        ((LinkedList<String>) linkedList).addFirst("Hello");
        System.out.println("Arrays List and Linked List equals =" +
                arraysList.equals(linkedList));
    }

}
