package org.sobngwi.oca.functional;

public class ClosureExample {


    private int res = 100 ; // can be updated inside Lamda expression

    Thread makeThreadClosure( final String s, final int n){
        return new Thread( () -> System.out.println((s + (res  += n ))));
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new ClosureExample().makeThreadClosure("result = ",  10 );
        thread.start();
        thread.join();
    }
}
