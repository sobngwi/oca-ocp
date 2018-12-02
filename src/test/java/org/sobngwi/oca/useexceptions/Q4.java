package org.sobngwi.oca.useexceptions;

public class Q4 {

    public static void main(String[] args) {

        try{
           throw new Exception("RT exception ") ;
        }
        catch (Throwable e ){} /* Required at least 1 catch or 1 finally*/
        /*finally {

        }*/
    }
}
