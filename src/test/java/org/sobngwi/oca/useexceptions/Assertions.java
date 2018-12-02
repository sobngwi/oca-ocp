package org.sobngwi.oca.useexceptions;

public class Assertions {

    public static void main(String[] args) {
        int n = 0 ;

        //boolean assert = false ; // Doesnt compile
        assert  n < 0: "Ohno";
        //assert  n < 0, "Ohno";
        //assert  n < 0 "Ohno";
        assert  (n < 0): "Ohno";
        assert  (n < 0): ("Ohno") + " XXX";
        assert n++ > 0;
        assert ++n > 0;
    }
}
