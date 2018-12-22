package org.sobngwi.oca.generics.shopping;

import java.util.ArrayList;
import java.util.List;

public class StockControl {

  public static void printInventory(List<Pair<ClothingItem>> lps) {
    for (Pair<ClothingItem> ps : lps) {
      System.out.println("Pair: " + ps);
    }
    // why not allowed below
    lps.add(new Pair<>(new Glove(5, "Blue"), new Glove(5, "Blue")));
  }
  public static void main(String[] args) {
    List<Pair<Shoe>> lps = new ArrayList<>();
    lps.add(new Pair(new Shoe(44, "Red"), new Shoe(44, "Red")));
//    printInventory(lps); // not allowed!!!
  }
}
