package org.sobngwi.oca.concurrency.state;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StatefullStream {

    private List<Integer> datas;
    private List<Integer> datasBk;

    @Before
    public void setUp() {
        datas =  new ArrayList<>();// Collections.synchronizedList(new ArrayList<>());
        datasBk =  new ArrayList<>();// Collections.synchronizedList(new ArrayList<>());
    }


    @Test
    public void stateFullLambdaShouldBeAvoid() {
        List<Integer> ints = IntStream
                .iterate ( 1 , i -> i + 1)
                .limit(10)
                .boxed()
                .collect(Collectors.toList());

        ints
                .parallelStream()
                .map(i -> {datasBk.add(i); return i ;})
                .forEachOrdered(i -> datas.add(i));

                    assertThat(datas.toString(), not(equalTo(datasBk.toString())));
                }
}
