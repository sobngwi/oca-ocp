package org.sobngwi.oca.functional.all;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Concordance {

    private static final Pattern WORD_BREAK = Pattern.compile("\\W+");
    private static final Comparator<Map.Entry<String, Long>> byValueOrder = Map.Entry.comparingByValue();
    private static final Comparator<Map.Entry<String, Long>> byValueOrderReverse = byValueOrder.reversed();
    private static final Predicate<String> isEmpty = String::isEmpty;


    @Test
    public void concordance() throws IOException {

                Files
                        .lines(Paths.get("/Users/sobngwi/intelliJ/codebase/certification/oca/src/PrideAndPrejudice.txt"))
                        .flatMap(WORD_BREAK::splitAsStream)
                        .filter(isEmpty.negate())
                        .map(String::toLowerCase)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .sorted(byValueOrderReverse)
                        .limit(10)
                        .map(l -> String.format("%20s  :  %5d", l.getKey(), l.getValue()))
                        .forEach(System.out::println);


    }
}
