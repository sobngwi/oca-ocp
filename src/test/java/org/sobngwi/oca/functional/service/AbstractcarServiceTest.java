package org.sobngwi.oca.functional.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sobngwi.oca.functional.model.Car;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class AbstractcarServiceTest {

    protected static List<Car> concurentCarsList;
    protected static List<Car> cars;
    protected static CarService carServiceMock;
    protected static CarService carService;
    protected static Map<String, Car> owner= new HashMap<>();
    protected static Map<String, List<Car>> ownerMultipleCars= new HashMap<>();
    protected static Car  alainCar, lineCar, bertinCar, patriciaCar, flaubertCar, nicoleCar;

    @Before
    public void setUp() {

        carService = new CarService();
    }

    @BeforeClass
    public static  void init(){
        alainCar =  Car.withGasColorPassengers(8,"Black","Alain", "Rudy", "Anne-Gaelle");
        lineCar = Car.withGasColorPassengers(5,"White", "Line", "Farel", "Kassyle", "Aurel", "Merveille");
        bertinCar = Car.withGasColorPassengers(7, "Black", "Bertin", "Virginie", "Marc-Antony");
        patriciaCar = Car.withGasColorPassengers(4, "White", "Patricia", "Tata Henriette", "Adriel", "Bebe Massi");
        flaubertCar = Car.withGasColorPassengersAndTrunk(10, "Red", "Totue","Malika", "Chiara");
        nicoleCar = Car.withGasColorPassengers(4, "Blue", "Nicole" );
        cars =
        Arrays.asList(
                alainCar,
                lineCar,
                bertinCar,
                patriciaCar,
                nicoleCar,
                flaubertCar
        );

        concurentCarsList = Collections.unmodifiableList(cars);
        ownerMultipleCars =
                cars
                .stream()
                .collect(Collectors.groupingBy( x -> x.getPassengers().get(0)));

        owner.put("Alain", alainCar);
        owner.put("Line",  lineCar);
        owner.put("Bertin", bertinCar);
        owner.put("Patricia", patriciaCar);
        owner.put("Totue", flaubertCar);
        owner.put("Nicole", nicoleCar);

        carServiceMock = mock(CarService.class);
        doCallRealMethod().when(carServiceMock).showAll(anyCollection());
        doCallRealMethod().when(carServiceMock).getRedCars(anyCollection());
    }
}
