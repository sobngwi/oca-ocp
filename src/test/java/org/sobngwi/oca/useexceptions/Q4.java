package org.sobngwi.oca.useexceptions;

public class Q4 {

    static class C implements AutoCloseable{

        @Override
        public void close()  {

        }
    }
    static class D implements AutoCloseable{

        @Override
        public void close()  {

        }
    }

    public static void main(String[] args) {

        try {
            throw new Exception("RT exception ");
        } catch (Throwable e) {
        } /* Required at least 1 catch or 1 finally*/
        /*finally {

        }*/


        try (C c = new C(); D d = new D()) {

        }
    }
}
