package org.sobngwi.oca.collections;

import java.util.TreeSet;

public class Q2 {
    public static void main(String[] args) {
        TreeSet<String> tree = new TreeSet<String>();
        tree.add("one");
        tree.add("One");
        tree.add("ONE");
        System.out.println(tree);
        System.out.println(tree.ceiling("On"));
    }
}
