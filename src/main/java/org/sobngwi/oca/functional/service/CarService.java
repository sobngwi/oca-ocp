package org.sobngwi.oca.functional.service;

import org.sobngwi.oca.functional.model.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class CarService {

   private static  Logger log = Logger.getLogger(CarService.class.getName());

    public void  showAll(Iterable<Car> cars){
        for (Car car: cars) {
           log.info(car.toString());
        }
        log.info("------------------------------------------------");
    }

    public static List<Car> getRedCars( Iterable<Car> input){
        List<Car> output = new ArrayList<>();
        for (Car car: input)
            if (car.getColor().equals("Red")) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }

    public static List<Car> getCarsByColor( Iterable<Car> input,  String color){
        List<Car> output = new ArrayList<>();
        for (Car car: input)
            if (car.getColor().equalsIgnoreCase(color)) {
                output.add(car);
            }
        return Collections.unmodifiableList(output);
    }
}
