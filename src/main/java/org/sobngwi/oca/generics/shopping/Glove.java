package org.sobngwi.oca.generics.shopping;

public class Glove extends ClothingItem implements Sized, Colored {
  private int size;
  private String color;

  public Glove(int size, String color) {
    this.size = size;
    this.color = color;
  }

  public int getSize() {
    return size;
  }

  public String getColor() {
    return color;
  }

  @Override
  public String toString() {
    return "Glove {" +
        "size=" + size +
        ", color='" + color + '\'' +
        '}';
  }

}
