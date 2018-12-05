package org.sobngwi.oca.locale;

import java.util.Locale;

public class Locales {


    public static void main(String[] args) {
        Locale fr = Locale.getDefault();
        System.out.println(fr);

        Locale.setDefault(Locale.US);
        System.out.println(Locale.getDefault());
        Locale cm = new Locale.Builder()
                .setLanguage("fr")
                .setRegion("CM")
                .build();
        System.out.println(cm);

    }
}
