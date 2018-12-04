package org.sobngwi.oca.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q3 {


    public static void main(String[] args) {
        Map<String, Double> map = new HashMap<>();
        map.put("pi", 3.14159); // map.add is not a map methode

       // What is the result of the following statements?
         List list = new ArrayList();
         list.add("one");
         list.add("two");
         list.add(7);
         for (Object  s: list) // ( String s: list) --> This would not compile
            System.out.print(s);


    }
}
