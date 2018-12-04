package org.sobngwi.oca.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Q10 {
    static  class MyComparator implements Comparator<String> {

        public int compare(String a, String b) {

            return b.toLowerCase().compareTo(a.toLowerCase());

        }
    }

            public static void main(String[] args) {

                String[] values = { "123", "Abb", "aab" }; ;//{"a", "z", "A", "Z", "1", "9"};//

                Arrays.sort(values, new MyComparator());

                for (String s: values)

                    System.out.print(s + " ");


               // What is the result of the following code?
                Comparator<Integer> c = (o1, o2) -> o2 - o1;
                List<Integer> list = Arrays.asList(5, 4, 7, 1);
                Collections.sort(list, c);
                System.out.println("index position is : " + Collections.binarySearch(list, 4));

            }

}
