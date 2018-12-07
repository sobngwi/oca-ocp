package org.sobngwi.oca.useexceptions;



import java.io.IOException;

public class Q13 {
    static class A extends Exception{}
    static class B extends A{}
    static class C extends IOException{
         public  void m() throws C{ }
    }
    static class D extends Throwable  {
        public void m() throws D{}}

    public static void main(String[] args) {
        try {
            new C().m();
            new D().m();
            throw new A();
        } catch (A | RuntimeException a /* in Multi Catch Env  a is effectively final */) {

          //  a = new B();
        }
        catch (C  c){
                C x = c ;
        }
        catch (D  d){
            D x = d ;
        }

    }
}
