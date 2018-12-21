package org.sobngwi.oca.exam;

public class Color {

    private int hue = 10;
 public class Shade {
 public int hue = Color.this.hue;
 }
public static void main(String[] args) {
        System.out.println(new Color().new Shade().hue); // new Shade().hue); does not compile
 }
}
