package org.sobngwi.oca.functional.service;

import org.junit.Ignore;
import org.junit.Test;
import org.sobngwi.oca.functional.model.Car;

import java.util.List;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CarServiceTest extends AbstractcarServiceTest{

    @Test
    public void shouldSetUpTheCarList() {

        //carService.showAll(cars);
        carServiceMock.showAll(cars);

        assertThat(cars.size(), equalTo(6));
        assertNotNull(carService);
        assertNotNull(carServiceMock);
        verify(carServiceMock, times(1));

    }

    @Test
    public void justBlackCars() {

        List<Car> result = carServiceMock.getRedCars(cars);

        assertTrue(result.size() == 1);
        assertThat(result.get(0).getColor(), equalTo("Red"));

    }

    @Test
    public void getCarsByColor_WhiteOneis() {

        List<Car> result = carService.getCarsByColor(cars, "white");

        assertTrue(result.size() == 2);
        assertThat(result.toString(),
                allOf(containsString("Line"), containsString("Patricia")) );
    }
}