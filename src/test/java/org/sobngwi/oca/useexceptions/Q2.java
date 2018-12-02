package org.sobngwi.oca.useexceptions;

public class Q2 {

    public static class  StuckTuekeyCage implements AutoCloseable{

        @Override
        public void close() throws Exception {
            throw new Exception("Cage door does not close");
        }
    }

    public static void main(String[] args) throws Throwable  {
        try(StuckTuekeyCage stuckTuekeyCage = new StuckTuekeyCage()){
            System.out.println("Put Turkey in");
            throw new Throwable("RT is the principal exception  ") ;
        }
        catch (Throwable e){
            System.out.println( "Catch the RT Exception ...");
        }
    }
}
