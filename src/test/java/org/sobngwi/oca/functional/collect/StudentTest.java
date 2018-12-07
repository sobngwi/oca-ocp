package org.sobngwi.oca.functional.collect;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class StudentTest {
    static Logger log = Logger.getLogger(StudentTest.class.getName());

    private static List<Student> school;
    private Map<String, List<Student>> resultTable;


    @Before
    public void setUp(){
        resultTable = school.stream()
                .collect(Collectors.groupingBy(student -> student.getGradeLetters()));
    }
    @BeforeClass
    public static void globalSetUp() {
        school = Arrays.asList(
                new Student("Fred", 71),
                new Student("Jim", 38),
                new Student("Sheila", 97),
                new Student("Weatherwax", 100),
                new Student("Ogg", 56),
                new Student("RinceWind", 28),
                new Student("Ridcully", 65),
                new Student("Magrat", 79),
                new Student("Valentine", 93),
                new Student("Gillian", 87),
                new Student("Anne", 91),
                new Student("Alain Narc.", 88),
                new Student("Ender", 91),
                new Student("Hyrum", 72),
                new Student("Locke", 91),
                new Student("Bonzo", 57)
        );
        school.forEach(s -> log.finest(s.toString()));
    }

    @Test
    public void mapGroupingBy() {



        assertThat(resultTable.keySet().size(), equalTo(6));
    }

    @Test
    public void mapGroupinByFlooring() {


        assertThat(resultTable.get("A").toString(), containsString("Locke"));
        assertThat(resultTable.get("B").toString(), containsString("Alain Narc"));
        assertThat(resultTable.get("B").toString(), not(containsString("Bonzo")));
        assertThat(resultTable.get("E").toString(), containsString("Bonzo"));

    }

    @Test
    public void groupingBykeysOrder() {

        Comparator<Map.Entry<String, List<Student>>> comp = comparingByKey();

        final List<Map.Entry<String, List<Student>>> collect = school.stream()
                .collect(Collectors.groupingBy(student -> student.getGradeLetters()))
                .entrySet()
                .stream()
                .sorted(comp)
                .collect(Collectors.toList());
        assertThat(collect.get(0).getKey(), equalTo("A"));
        assertThat(collect.get(collect.size() -1 ).getKey(), equalTo("F"));
        assertThat(collect.get(0).getValue().toString(), containsString("Weatherwax"));

    }


    @Test
    public void downstreamCollector() {
        //Map<String, Long> resultTable =;
        Comparator<Map.Entry<String, Long>> comp = comparingByValue();
        comp = comp.reversed();
        final List<Map.Entry<String, Long>> sortedMapByvalue = school.stream()
                .collect(Collectors.groupingBy(student -> student.getGradeLetters(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(comp)
                .collect(Collectors.toList());

        assertThat(sortedMapByvalue.get(0).getValue(), equalTo(6L));
        assertThat(sortedMapByvalue.get(sortedMapByvalue.size() -1 ).getValue(), equalTo(1L));
    }


    @Test
    public void homeWorkCollectingAndThen() {

        Comparator<Map.Entry<String, Student>> valueOrderd = comparingByKey();
        String maxGraduate = school.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(Student::getScore)),
                        (student)-> student.isPresent()? student.get().getName():"none"));
        assertThat(maxGraduate, CoreMatchers.equalTo("Weatherwax"));

        String minGraduate = school.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.minBy(Comparator.comparing(Student::getScore)),
                        (student)-> student.isPresent()? student.get().getName():"none"));
        assertThat(minGraduate, CoreMatchers.equalTo("RinceWind"));

       String result =  school.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.groupingBy(s -> s.getGradeLetters()),
                        s -> s.entrySet()
                                .stream()
                                .map(s1 -> s1.getValue()
                                        .stream()
                                        .map(Student::getName)
                                        .reduce("", (s2, s3) -> s2 + " " + s3)
                                        + " has(ve) Grade " + s1.getKey() + "\n")))
               .collect(Collectors.joining());

       assertThat(result, containsString("Sheila Weatherwax Valentine Anne Ender Locke has(ve) Grade A"));
       assertThat(result, containsString("Jim RinceWind has(ve) Grade F"));
       assertThat(result, containsString("Ridcully has(ve) Grade D"));
               // .forEach(System.out::println);
    }
}