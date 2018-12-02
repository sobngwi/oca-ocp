package org.sobngwi.oca.patterns;

public class Singleton {

    private static volatile Singleton instance = null;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {

        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    synchronized (Singleton.class) {
                        instance = new Singleton();
                    }
                }
            }
        }
        return instance;
    }

}
