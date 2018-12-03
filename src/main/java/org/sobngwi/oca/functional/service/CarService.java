package org.sobngwi.oca.functional.service;

import org.sobngwi.oca.functional.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;


public class CarService {

    private static Logger log = Logger.getLogger(CarService.class.getName());

    public void showAll(Iterable<Car> cars) {
        for (Car car : cars) {
            log.info(car.toString());
        }
        log.info("------------------------------------------------");
    }

    class PassengerCountOrder implements Comparator<Car> {

        @Override
        public int compare(Car o1, Car o2) {
            return o1.getPassengers().size() - o2.getPassengers().size();
        }
    }

    interface CarCriterion {
        boolean test(Car car);
    }

    class RedCarCriterion implements CarCriterion {

        @Override
        public boolean test(Car c) {
            return c.getColor().equals("Red");
        }
    }

    class GasLevCarCriterion implements CarCriterion {
        private final int threshold;

        public GasLevCarCriterion(int threshold) {
            this.threshold = threshold;
        }

        @Override
        public boolean test(Car c) {
            return c.getGasLevel() >= threshold;
        }
    }

    public static List<Car> getRedCars(Iterable<Car> input) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (car.getColor().equals("Red")) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public static List<Car> getCarsByCriterion(Iterable<Car> input, CarCriterion criterion) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (criterion.test(car)) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public static List<Car> getCarsByColor(Iterable<Car> input, String color) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (car.getColor().equalsIgnoreCase(color)) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public static List<Car> getCarsByGasLevel(Iterable<Car> input, int gasLevel) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (car.getGasLevel() >= gasLevel) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

}
