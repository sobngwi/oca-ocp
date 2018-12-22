package org.sobngwi.oca.generics.simple;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UseAList {
  public static void breakAList(List l) {
    l.add(LocalDate.now());
    l.add("Alain");
  }

  public static void breakAnArray(Object[] oa) {
    oa[0] = LocalDate.now();
  }
  public static void main(String[] args) {
    List<String> names = new ArrayList(); // Actually a list of Object!!!
    names.add("Fred");
    names.add("Jim");
    names.add("Sheila");

//    names.add(LocalDate.now());
    System.out.println("First name in list is " + names.get(0));

    String n2 = /*(String)*/names.get(2); // get returns an Object!
    System.out.println(n2);

    breakAList(names);

//    String n3 = /*(String)*/names.get(3); // get returns an Object!
//    System.out.println(n3);

    List<LocalDate> lld;

    String[] sa = {"","",""};
    breakAnArray(sa);

  }
}
