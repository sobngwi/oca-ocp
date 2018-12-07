package org.sobngwi.oca.functional.all;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Concordance {

    private static final Pattern WORD_BREAK = Pattern.compile("\\W+");
    private static final Comparator<Map.Entry<String, Long>> byValueOrder = Map.Entry.comparingByValue();
    private static final Comparator<Map.Entry<String, Long>> byValueOrderReverse = byValueOrder.reversed();
    private static final Predicate<String> isEmpty = String::isEmpty;


    @Test
    @Ignore
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

    @Test
    public void concordanceWithExceptionHandling() throws IOException {

        String relativePath = "/Users/sobngwi/intelliJ/codebase/certification/oca/src/";
        List<String> fileNames = Arrays.asList(relativePath + "Bad Book.txt", relativePath + "PrideAndPrejudice.txt", relativePath + "ACopy.txt");

        fileNames
                .stream()

                //.map(fileName -> Paths.get(fileName))
                //.flatMap(Files::lines)
                .map(Paths::get)
                //.map(Concordance::lines)
                .map(wrapException( p -> Files.lines(p)))
                .peek(o -> {
                    if (!o.isPresent()) System.err.println("Bad file !");
                })
                .filter(Optional::isPresent)
                .flatMap(Optional::get)
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

    private static Optional<Stream<String>> lines(Path p) {
        try {
            return Optional.of(Files.lines(p));
        } catch (IOException ioe) {
            // Sol 1  throw new RuntimeException( ioe) ;
            System.err.println(ioe);
            return Optional.empty(); // sol 2 ignore the exception. ie the files.
        }
    }

    private interface exceptionFunction<E, F> {
        F apply(E e) throws Exception;
    }

    private static <E, F> Function<E, Optional<F>> wrapException(exceptionFunction<E, F> op) {

        return e -> {
            try {
                return Optional.of(op.apply(e));
            } catch (Throwable t) {
                System.err.println(t.getMessage());
                return Optional.empty();
            }
        };
    }
}
