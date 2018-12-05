package org.sobngwi.oca.functional.pure;


import org.junit.Before;
import org.junit.Test;
import org.sobngwi.oca.functional.model.Car;
import org.sobngwi.oca.functional.service.AbstractcarServiceTest;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
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

}