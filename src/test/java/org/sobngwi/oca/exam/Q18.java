package org.sobngwi.oca.exam;

import java.util.ArrayList;
import java.util.List;

public class Q18 {

    public static void main(String[] args) {
        List list = new ArrayList();

        list.add(1);
        list.add("2");
        list.add(.3);
        print(list);
    }

    private static void print(List<String> list) {
        for (Object object: list)
            System.out.print(object);
    }
}
