package org.sobngwi.oca.exam;

public class Q14{
    interface Furry { }
    static class Chipmunk { }
    class FurryChipmunk implements Furry { }
    public static void main(String[] args) {
        Chipmunk c = new Chipmunk();
        int result = 0;
        if (c instanceof Q14.Furry) result += 1;
        if (c instanceof Q14.Chipmunk) result +=2;
        if (null instanceof Q14.FurryChipmunk) result += 4;
        System.out.println(result);
    } }
