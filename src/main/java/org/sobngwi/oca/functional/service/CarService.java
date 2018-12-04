package org.sobngwi.oca.functional.service;

import org.sobngwi.oca.functional.model.Car;

import java.util.*;
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

    public interface Criterion<T> {
        boolean test(T t);

        Criterion<Car> RED_CAR_CRITERION = c ->  c.getColor().equals("Red");
        Criterion<Car> GAZ_LEVEL_CAR_CRITERION = c -> c.getGasLevel() >=7;
        Criterion<Car> RED_CAR_CRITERION_AND_GAZ_LEVEL_CAR_CRITERION =
                and(RED_CAR_CRITERION, GAZ_LEVEL_CAR_CRITERION );
        Criterion<Car> GAZ_LEVEL_CAR_CRITERION_UNDER7 = negate(GAZ_LEVEL_CAR_CRITERION);
        Criterion<Car> COLORIZED_CRITERION = getColorCriterion("Black");

        static Criterion<Car> negate(Criterion<Car> criterion) {
            return c -> ! criterion.test(c) ;
        }

        static  Criterion<Car> and(Criterion<Car> first, Criterion<Car> second) {
            return c -> first.test(c) && second.test(c) ;
        }
    }

  /*  public static final Criterion<Car> RED_CAR_CRITERION = c ->  c.getColor().equals("Red");
    public static final Criterion<Car> GAZ_LEVEL_CAR_CRITERION = c -> c.getGasLevel() >=7;
    public static final Criterion<Car> RED_CAR_CRITERION_AND_GAZ_LEVEL_CAR_CRITERION =
            and(RED_CAR_CRITERION, GAZ_LEVEL_CAR_CRITERION );
    public static final Criterion<Car> GAZ_LEVEL_CAR_CRITERION_UNDER7 = negate(GAZ_LEVEL_CAR_CRITERION);
*/

    /*class RedCarCriterion implements Criterion<Car> {

        @Override
        public boolean test(Car c) {
            return c.getColor().equals("Red");
        }
    }*/

    /*private class GasLevCarCriterion implements Criterion<Car> {
        private final int threshold;

        public GasLevCarCriterion(int threshold) {
            this.threshold = threshold;
        }

        @Override
        public boolean test(Car c) {
            return c.getGasLevel() >= threshold;
        }
    }*/

    /* function that create a function as return value ...*/
     private static  Criterion<Car> getColorCriterion(final String... colors ){
        Set<String> colorSet = new HashSet<>(Arrays.asList(colors));

        return c -> colorSet.contains(c.getColor());
    }


    public  List<Car> getRedCars(Iterable<Car> input) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (car.getColor().equals("Red")) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public List<Car> getCarsByCriterion(Iterable<Car> input, Criterion criterion) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (criterion.test(car)) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public  List<Car> getCarsByColor(Iterable<Car> input, String color) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (car.getColor().equalsIgnoreCase(color)) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public  List<Car> getCarsByGasLevel(Iterable<Car> input, int gasLevel) {

        List<Car> output = new ArrayList<>();

        for (Car car : input)
            if (car.getGasLevel() >= gasLevel) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

}
