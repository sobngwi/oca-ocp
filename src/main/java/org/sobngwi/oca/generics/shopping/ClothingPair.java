package org.sobngwi.oca.generics.shopping;

import java.io.Serializable;

// A SINGLE class MAY be included in the constraints, but
// must be the first-mentioned constraint
//public class ClothingPair<F extends /*ClothingItemClass & */Sized & Colored/*, String*/> extends Pair<F> {
public class ClothingPair<E extends ClothingItem & Sized & Colored> extends Pair<E> {
//  String c = "Hello";

  public ClothingPair(E left, E right) {
    super(left, right);
  }
  public /*<G> */boolean isMatched() {
    return left.getSize() == right.getSize()
        && left.getColor().equals(right.getColor());
  }

  public static <E extends Sized & Colored> boolean isMatched(E left, E right){
    return left.getSize() == right.getSize()
            && left.getColor().equals(right.getColor());
  }

  public static <F extends ClothingItem & Sized & Colored>
  boolean match(F left, F right) {
    return left.getSize() == right.getSize()
        && left.getColor().equals(right.getColor());
  }
}
