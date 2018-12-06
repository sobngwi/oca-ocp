package org.sobngwi.oca.functional.model;

import java.util.*;

public class Car {
    private int gasLevel;
    private final String color ;
    private final List<String> passengers;
    private final List<String> trunkContents;

    private Car(int gasLevel, String color, List<String> passengers, List<String> trunkContents) {
        this.gasLevel = gasLevel;
        this.color = color;
        this.passengers = passengers;
        this.trunkContents = trunkContents;
    }

    public static  Car withGasColorPassengers(
            int gasLevel, String color, String... passengers) {
        List<String> imutablesPassengers = Collections.unmodifiableList(Arrays.asList(passengers));
        return new Car(gasLevel, color, imutablesPassengers, null );
    }


    public static  Car withGasColorPassengersAndTrunk(
            int gasLevel, String color, String... passengers) {
        List<String> imutablesPassengers = Collections.unmodifiableList(Arrays.asList(passengers));
        List<String> trunkContents = Collections.unmodifiableList(Arrays.asList("java books", "whisky Jack Daniel", "spare wheeel"));
        return new Car(gasLevel, color, imutablesPassengers, trunkContents);
    }

    public int getGasLevel() {
        return gasLevel;
    }
    public Car addGazLevel (  int gazLevelToAdd){
        return new Car(this.gasLevel + gazLevelToAdd, this.color, this.passengers, trunkContents);

    }

    public String getColor() {
        return color;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public List<String> getTrunkContents() {
        //return trunkContents == null ? Collections.unmodifiableList(Arrays.asList()) : trunkContents ;
        return  trunkContents ;
    }

    public Optional <List<String>> getTrunkContentsOpt() {
        //return trunkContents == null ? Collections.unmodifiableList(Arrays.asList()) : trunkContents ;
        return  Optional.ofNullable(trunkContents) ;
    }


    @Override
    public String toString() {
        return "Car{" +
                "gasLevel=" + gasLevel +
                ", color='" + color + '\'' +
                ", passengers=" + passengers +
                ", trunkContents=" + getTrunkContents() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;
        Car car = (Car) o;
        return getGasLevel() == car.getGasLevel() &&
                getColor().equals(car.getColor()) &&
                getPassengers().equals(car.getPassengers()) &&
                getTrunkContents().equals(car.getTrunkContents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGasLevel(), getColor(), getPassengers(), getTrunkContents());
    }
}
