package org.sobngwi.oca.chap1;

public class Outer {

    private int x = 5;

    private static class Inner {
        public static int x = 10;

        public static void go() {
            System.out.println(x);
        }

        private void nonStaticMethod(){
            System.out.println(x);
        }
    }

    public static void main(String[] args) {
        Outer out = new Outer();
        Inner inner = new Inner();
        Outer.Inner in = inner;
                inner.go();
                in.go();
    }
}
