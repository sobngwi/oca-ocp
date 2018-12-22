package org.sobngwi.oca.generics.simple;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ToArray {

  // "heap pollution!"
  @SafeVarargs
  public static <E> void useStuff(E ... values) {}

  public static <E> E[] asArray(List<E> l, Class<E> classTag) {
    E[] rv = (E[]) Array.newInstance(classTag, l.size());
    for (int idx = 0; idx < rv.length; idx++) {
      rv[idx] = l.get(idx);
    }
    return rv;
  }

  public static void main(String[] args) {
    List<String> ls = Arrays.asList("Fred", "Jim", "Sheila");
    String[] sa = asArray(ls, String.class);
    System.out.println("Type of array is " + sa.getClass().getName());
    for (String s : sa) {
      System.out.println(">> " + s);
    }

//    Class<?> cl = "".getClass();
    Class<String> cl = String.class;
    System.out.println("Class of String is " + cl.getName());
//    Method[] ma = cl.getMethods();
//    for (Method m : ma) {
//      System.out.println("> " + m);
//    }

//    sa[0] = LocalDate.now();
//    LocalDate[] lda = asArray(ls);
  }
}
