package org.sobngwi.oca.locales;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Q10 {

    public static void main(String[] args) {
        //Q. 10 	What is the output of the following code?
        LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
        Period p = Period.of(1, 2, 3);
        d = d.minus(p);
        DateTimeFormatter f = DateTimeFormatter.
                ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(d.format(f));
    }

}
