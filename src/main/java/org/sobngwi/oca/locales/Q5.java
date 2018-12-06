package org.sobngwi.oca.locales;

import java.util.Locale;
import java.util.ResourceBundle;

public class Q5 {


    public static void main(String[] args) {
        Locale fr = new Locale("fr");
        Locale.setDefault(new Locale("en", "US"));
        ResourceBundle b = ResourceBundle.getBundle("Dolphins", fr);
        System.out.println("name = " + b.getString("name"));
        System.out.println("age = " + b.getString("age"));
    }
}
