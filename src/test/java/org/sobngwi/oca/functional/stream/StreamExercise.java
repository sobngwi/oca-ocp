package org.sobngwi.oca.functional.stream;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamExercise {


    @Test (expected = ConcurrentModificationException.class)
    public void peekCanGenerateConcurrentModification() {
        // Create a list of 10 integers in the open range [0..10).
        List<Integer> list = IntStream
                .range(0, 10)
                .boxed()
                .collect(toList());

        Stream<Integer> stream = list
                // Convert the list of integers into a stream of integers.
                .stream();

            stream
                    // Convert the list into parallel stream.
                    .parallel();
            stream
                // Improperly modify the stream during its processing.
                // This should generate a ConcurrentModificationException
                .peek(list::remove)

                // Print out the results of the stream.
                .forEach(System.out::print);
    }
}
