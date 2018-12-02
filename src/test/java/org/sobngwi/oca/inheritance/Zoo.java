package org.sobngwi.oca.inheritance;

public class Zoo {

    public static void main(String[] args) {

        Animal animal = new Animal();
        Cat cat = new Cat();
        Cat cat1 = (Cat) new Animal();

    }

    private interface I{}
}
