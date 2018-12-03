package org.sobngwi.oca.functional.service;

import org.junit.Test;
import org.sobngwi.oca.functional.model.Car;

import java.util.List;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CarServiceTest extends AbstractcarServiceTest {

    @Test
    public void shouldSetUpTheCarList() {
        //carService.showAll(cars);
        carServiceMock.showAll(concurentCarsList);

        assertThat(concurentCarsList.size(), equalTo(6));
        assertNotNull(carService);
        assertNotNull(carServiceMock);
        verify(carServiceMock, times(1));

    }

    @Test
    public void justRedCars() {

        List<Car> result = carServiceMock.getRedCars(concurentCarsList);

        assertTrue(result.size() == 1);
        assertThat(result.get(0).getColor(), equalTo("Red"));
    }

    @Test
    public void justRedCarsByCarCriterion() {

        List<Car> result = carService.getCarsByCriterion(concurentCarsList, carService.new RedCarCriterion());

        assertTrue(result.size() == 1);
        assertThat(result.get(0).getColor(), equalTo("Red"));
    }

    @Test
    public void getCarsByColor_WhiteOne() {

        List<Car> result = carService.getCarsByColor(concurentCarsList, "white");

        assertTrue(result.size() == 2);
        assertThat(result.toString(),
                allOf(containsString("Line"), containsString("Patricia")));
    }

    @Test
    public void getCarsByGasLevel() {

        List<Car> result = carService.getCarsByGasLevel(concurentCarsList, 7);

        assertTrue(result.size() == 3);
        assertThat(result.toString(),
                allOf(containsString("Bertin"), containsString("Virginie")));
    }

    @Test
    public void getCarsByGasLevelByCriterion() {

        List<Car> result = carService.getCarsByCriterion(concurentCarsList, carService.new GasLevCarCriterion((7)));

        assertTrue(result.size() == 3);
        assertThat(result.toString(),
                allOf(containsString("Bertin"), containsString("Virginie")));
    }

    @Test
    public void orderCarsByPassengersSize() {

        cars.sort(carService.new PassengerCountOrder());

        assertThat(cars.get(0).getPassengers().toString(), containsString("Nicole"));
        assertThat(cars.get(cars.size() - 1).getPassengers().toString(), allOf(containsString("Line"),
                containsString("Farel")));
    }
}