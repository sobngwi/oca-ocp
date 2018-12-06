package org.sobngwi.oca.functional.pure;


import org.junit.Before;
import org.junit.Test;
import org.sobngwi.oca.functional.model.Car;
import org.sobngwi.oca.functional.service.AbstractcarServiceTest;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;


public class SuperIterableTest extends AbstractcarServiceTest {

    private SuperIterable<String> strings;
    private SuperIterable<Car> carsSuperIterable;

    @Before
    public void setUp() {
        strings = new SuperIterable<>(Arrays.asList("LigthCoral", "pink", "Orange",
                "plum", "Blue", "limergen"));
        carsSuperIterable = new SuperIterable<>(cars);
    }

    @Test
    public void iterator() {
        for (String s : strings) {
            assertThat(strings.toString(), containsString(s));
        }
    }

    @Test
    public void forEvery() {
        strings.forEvery(
                s -> assertThat(strings.toString(), containsString(s))
        );
    }

    @Test
    public void filter() {
        SuperIterable<String> result = strings.filter(s -> Character.isUpperCase(s.charAt(0)));

        assertThat(result.toString(), allOf(containsString("LigthCoral"),
                containsString("Orange"), containsString("Blue")));
        assertThat(result.toString(),
                allOf(not(containsString("plum")), not(containsString("limergen"))));
    }

    @Test
    public void shouldReturnTextualRepresentationOfSuperIterable() {

        assertThat(strings.toString(), allOf(containsString("LigthCoral"),
                containsString("Orange"), containsString("Blue"),
                (containsString("plum")), containsString("limergen")));
    }

    @Test
    public void filterWithForEvery() {
        SuperIterable<String> result = strings.filterWithForEvery(s -> Character.isUpperCase(s.charAt(0)));

        assertThat(result.toString(), allOf(containsString("LigthCoral"),
                containsString("Orange"), containsString("Blue")));
        assertThat(result.toString(),
                allOf(not(containsString("plum")), not(containsString("limergen"))));
    }

    @Test
    public void map() {

        SuperIterable<String> result = strings.map(s -> s.toUpperCase());
        strings.forEvery(
                s -> assertThat(result.toString(), containsString(s.toUpperCase())));
    }

    @Test
    public void mapAddGazLevel() {

        final int gazToAdd = 100;
        SuperIterable<Car> result = carsSuperIterable.map(car -> car.addGazLevel(gazToAdd));
        carsSuperIterable.forEvery(
                car -> assertThat(result.toString(), containsString((car.getGasLevel() + gazToAdd) + "")));
    }

    @Test
    public void filterAndMap() {
        SuperIterable<String> result = strings.filter(s -> Character.isUpperCase(s.charAt(0)))
                .map(s -> s.toUpperCase());
        assertThat(result.toString(), allOf(containsString("LigthCoral".toUpperCase()),
                containsString("Orange".toUpperCase()), containsString("Blue".toUpperCase())));
        assertThat(result.toString(),
                allOf(not(containsString("plum")), not(containsString("limergen"))));
    }

    @Test
    public void flatMap() {

        SuperIterable<String> result = carsSuperIterable.flatMap(car -> new SuperIterable<>(car.getPassengers())
                .filter(passengerName -> passengerName.contains("ri")));

        assertThat(result.toString(), allOf(containsString("Patricia"),
                containsString("Henriette"), containsString("Adriel")));
    }

    @Test
    public void optOfTrunktestWithoutTrunkContent() {
        Optional<Map<String, Car>> ownerOpt = Optional.of(owner);

        assertThat(
                ownerOpt.map(v -> v.get("Alain"))
                        .map(x -> x.getTrunkContents())
                        .isPresent(), equalTo(false));
        assertThat(
                ownerOpt.map(v -> v.get("Wrong Owner"))
                        .map(x -> x.getTrunkContents())
                        .isPresent(), equalTo(false));
        assertThat(
                ownerOpt.map(v -> v.get("Wrong Owner"))
                        .map(x -> x.getTrunkContentsOpt())
                        .isPresent(), equalTo(false));
    }


    @Test
    public void optOfTrunktestWithTrunkContent() {
        Optional<Map<String, Car>> ownerOpt = Optional.of(owner);

        assertThat(
                ownerOpt.map(v -> v.get("Totue"))
                        .map(x -> x.getTrunkContents())
                        .isPresent(), equalTo(true));
        assertThat(
                ownerOpt.map(v -> v.get("Totue"))
                        .flatMap(x -> x.getTrunkContentsOpt())
                        .isPresent(), equalTo(true));

        assertThat(
                ownerOpt.map(v -> v.get("Totue"))
                        .flatMap(x -> x.getTrunkContentsOpt())
                        .get().toString(),
                containsString("whisky Jack Daniel"));
        assertThat(
                ownerOpt.map(v -> v.get("Totue"))
                        .flatMap(x -> x.getTrunkContentsOpt())
                        .get().size(),
                equalTo(3));
    }

    @Test
    public void optionalOnFlatMaps() {
        ownerMultipleCars.merge("Totue",Arrays.asList(lineCar),
                (s,t) -> Arrays.asList(flaubertCar, flaubertCar.addGazLevel(100)));

        assertThat(ownerMultipleCars.get("Totue")
                .stream()
                //.map( c -> c.getTrunkContents())

                //.flatMap(List::stream)
                .flatMap(l -> l.getTrunkContents().stream())
                .parallel()
                //.filter(not(String::equals))
                //.map(Car::getPassengers getPassengers)
               // filter ( not empty)
                .count(), equalTo(6L));


    }
}