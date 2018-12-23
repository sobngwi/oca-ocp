package org.sobngwi.oca.collections.uselists;

import org.junit.Assert;
import org.junit.Test;
import org.sobngwi.oca.collections.AbstractCollections;
import org.sobngwi.oca.functional.annotation.SelectIterable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class Lists extends AbstractCollections {

    @Test(expected = ClassCastException.class)
    public void shouldThhrowExceptionInCollectionOfString() {

        addObject(strings);
        strings.forEach(System.out::println); // provoque un cast error
        fail();
    }

    @Test
    public void unModifiableListConstainsModifiableElements() {

        StringBuilder result = stringBuilders.get(0).append(" SOBNGWI");

        assertThat(result.toString(), containsString("SOBNGWI"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void canNotAddElementsToCollectionsUnmodifiables() {
        stringBuilders.add(new StringBuilder("SOBNGWI"));
        fail();
    }

    @Test
    public void should_sort_the_list_using_the_natural_order() {
        strings.clear();
        strings = Arrays.asList("A", "b", "Z", "z", "0", "1", "9", "X");


        // strings.sort(null); // java.lang.Comparable is the natural order to sort which is in CASE_INSENSITIVE_ORDER.
        strings.sort(String.CASE_INSENSITIVE_ORDER);
        assertThat(strings.get(0), equalTo("0"));
        assertThat(strings.get(strings.size() - 1), equalTo("z"));
        assertTrue(strings.indexOf("1") < strings.indexOf("9"));
        assertTrue(strings.indexOf("A") < strings.indexOf("Z"));

        //strings.add("C");
    }

    @Test
    public void shouldSortNumbersAndStringsInLowerCase_UpperCase() {
        strings.clear();
        strings = Arrays.asList("12", "Az", "aA", "Ab", "zZ", "99");
        strings.sort((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()));

        assertThat(strings, equalTo(
                Arrays.asList("99", "12", "Az", "aA", "Ab", "zZ")
                        .stream()
                        .sorted((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()))
                        .collect(Collectors.toList())));

        assertThat(strings, equalTo(Arrays.asList("zZ", "Az", "Ab", "aA", "99", "12")));
        assertThat(strings, equalTo(
                Arrays.asList("Az", "aA", "99", "Ab", "zZ", "12")
                        .stream()
                        .sorted((x, y) -> y.toUpperCase().compareTo(x.toUpperCase()))
                        .collect(Collectors.toList())));

        strings.sort(Comparator.comparing(String::toLowerCase));

        assertThat(strings, equalTo(Arrays.asList("12", "99", "aA", "Ab", "Az", "zZ")));
        assertThat(strings, equalTo(
                Arrays.asList("aA", "99", "Ab", "Az", "12", "zZ")
                        .stream()
                        .sorted(Comparator.comparing(String::toLowerCase))
                        .collect(Collectors.toList())));
        assertThat(strings, equalTo(
                Arrays.asList("12", "99", "aA", "Ab", "Az", "zZ")
                        .stream()
                        .sorted(Comparator.comparing(String::toUpperCase))
                        .collect(Collectors.toList())));

    }

    @Test
    public void shouldSortNumbersAndStringsInLowerCase_UpperCase_In_Parallel() {
        strings.clear();
        strings = Arrays.asList("12", "Az", "aA", "Ab", "zZ", "99");
        strings.sort((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()));

        assertThat(strings, equalTo(
                Arrays.asList("99", "12", "Az", "aA", "Ab", "zZ")
                        .stream()
                        .sequential()
                        .sorted((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()))
                        .collect(Collectors.toList())));
        assertThat(strings, equalTo(
                Arrays.asList("99", "12", "Az", "aA", "Ab", "zZ")
                        .stream()
                        .parallel()
                        .sorted((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()))
                        .collect(Collectors.toList())));
        assertThat(Arrays.asList("99", "12", "Az", "aA", "Ab", "zZ")
                .stream()
                .sequential()
                .sorted((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()))
                .collect(Collectors.toList()),
                equalTo(Arrays.asList("99", "12", "Az", "aA", "Ab", "zZ")
                        .stream()
                        .parallel()
                        .sorted((x, y) -> y.toLowerCase().compareTo(x.toLowerCase()))
                        .collect(Collectors.toList())));
    }

    @Test
    public void linkedListAndArraysListEqualsCompareTheirValues() {
        List<String> arraysList = Arrays.asList("Hello");
        List<String> linkedList = new LinkedList<>();
        linkedList.add("Hello");

        assertThat(arraysList, equalTo(linkedList));
    }

    @Test
    public void sortTheListInNaturalOder() {


        stringList.sort(Comparator.naturalOrder());

        assertThat(stringList.get(0), equalTo("NARCISSE"));
        assertThat(stringList.get(1), equalTo("SOBNGWI"));
        assertThat(stringList.get(2), equalTo("alain"));
        assertThat(stringList.get(3), equalTo("sagueu"));

        assertThat(Collections.min(stringList), equalTo("NARCISSE"));
        assertThat(Collections.max(stringList), equalTo("sagueu"));


    }

    @Test
    public void sortTheListInNaturalOderIgnoreCase() {

        //stringList= Arrays.asList("NARCISSE", "alain",  "SOBNGWI" , "sagueu");stringList.forEach( x -> System.out.print(" " + x ));
        stringList.sort(String.CASE_INSENSITIVE_ORDER);
        assertThat(stringList.get(0), equalTo("alain"));
        assertThat(stringList.get(1), equalTo("NARCISSE"));
        assertThat(stringList.get(2), equalTo("sagueu"));
        assertThat(stringList.get(3), equalTo("SOBNGWI"));
        // Even if the sort  have been perform by case sensitive, the min and max is performed in the natural order.
        assertThat(Collections.min(stringList), equalTo("NARCISSE"));
        assertThat(Collections.max(stringList), equalTo("sagueu"));

    }

    @Test
    public void dequePushIsLIFO() {
        ArrayDeque<String> greetings = new ArrayDeque<String>();
        greetings.push("hello");
        greetings.push("hi");
        greetings.push("ola");

        assertThat(greetings.getFirst(), equalTo("ola"));
        assertThat(greetings.getLast(), equalTo("hello"));
    }

    @Test
    public void dequeOfferIsFIFO() {
        ArrayDeque<String> greetings = new ArrayDeque<>();
        greetings.offer("hello");
        greetings.offer("hi");
        greetings.offer("ola");

        assertThat(greetings.getFirst(), equalTo("hello"));
        assertThat(greetings.getLast(), equalTo("ola"));
    }

    @Test
    public void bynarySearchNotOKOnScendingSortedCollections() {

        Comparator<Integer> c = (o1, o2) -> o2 - o1; // descending
        List<Integer> list = Arrays.asList(5, 4, 7, 1);
        Collections.sort(list, c);
        Assert.assertThat(Collections.binarySearch(list, 4), equalTo(-1));
        Assert.assertThat(Collections.binarySearch(list, 1), equalTo(-1));
    }

    @Test
    public void bynarySearchWorksOnlyOnScendingSortedCollections() {

        Comparator<Integer> c = (o1, o2) -> o1 - o2; // ascending
        List<Integer> list = Arrays.asList(5, 4, 7, 1);
        Collections.sort(list, c);
        Assert.assertThat(Collections.binarySearch(list, 4), equalTo(1));
        Assert.assertThat(Collections.binarySearch(list, 1), equalTo(0));
    }

    @Test
    public void selectIfPresent() {

        Map<String, Integer> iqMap =
                new ConcurrentHashMap<String, Integer>() {
                    {
                        put("Larry", 100);
                        put("Curly", 90);
                        put("Moe", 110);
                    }
                };

        List<String> result = SelectIterable.selectIf(iqMap.keySet(), x -> iqMap.get(x) > 100);
        assertFalse(result.isEmpty());
        assertThat(result.size(), equalTo(1));
        assertThat(result.toString(), containsString("Moe"));

    }

    @Test
    public void selectIfPresentByStream() {

        Map<String, Integer> iqMap =
                new ConcurrentHashMap<String, Integer>() {
                    {
                        put("Larry", 100);
                        put("Curly", 90);
                        put("Moe", 110);
                    }
                };


        List<Integer> result = iqMap.keySet()
                .stream()
                .filter(x -> iqMap.get(x) > 100)
                .map(x -> iqMap.get(x))
                .collect(Collectors.toList());
        assertFalse(result.isEmpty());
        assertThat(result.size(), equalTo(1));
        assertThat(result.toString(), containsString("110"));

    }
}
