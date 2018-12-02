package org.sobngwi.oca.functional.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.sobngwi.oca.functional.model.Car;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyCollection;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;

public class AbstractcarServiceTest {

    protected static List<Car> cars;
    protected static CarService carServiceMock;
    protected static CarService carService;

    @Before
    public void setUp() throws Exception {

        carService = new CarService();
    }

    @BeforeClass
    public static  void init(){
        cars = Collections.unmodifiableList(Arrays.asList(
                Car.withGasColorPassengers(8,"Black","Alain", "Rudy", "Anne-Gaelle"),
                Car.withGasColorPassengers(5,"White", "Line", "Farel", "Kassyle", "Aurel", "Merveille"),
                Car.withGasColorPassengers(7, "Black", "Bertin", "Virginie", "Marc-Antony"),
                Car.withGasColorPassengers(4, "White", "Patricia", "Tata Henriette", "Adriel", "Bebe Massi"),
                Car.withGasColorPassengers(4, "Blue", "Nicole" ),
                Car.withGasColorPassengersAndTrunk(10, "Red", "Totue","Malika", "Chiara")
        ));

        carServiceMock = mock(CarService.class);
        doCallRealMethod().when(carServiceMock).showAll(anyCollection());
        doCallRealMethod().when(carServiceMock).getRedCars(anyCollection());
    }
}
