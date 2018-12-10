package org.sobngwi.oca.concurrency.deque;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class DequeTest {


    @Test
    public void offerMeansLIFO() throws InterruptedException {

        BlockingDeque<Integer> integerDeque = new LinkedBlockingDeque<>();

        integerDeque.offer(103);
        integerDeque.offerFirst(20, 1, TimeUnit.SECONDS);
        integerDeque.offerLast(85, 7, TimeUnit.HOURS);

        assertThat(integerDeque.pollFirst(200, TimeUnit.NANOSECONDS), equalTo(20));
        assertThat(integerDeque.pollLast(1, TimeUnit.MINUTES),  equalTo(85));

    }

    @Test
    public void pushMeansFIFO() throws InterruptedException {

        BlockingDeque<Integer> integerDeque = new LinkedBlockingDeque<>();

        integerDeque.push(20);
        integerDeque.push(103);
        integerDeque.offerLast(85, 7, TimeUnit.HOURS);

        assertThat(integerDeque.poll(200, TimeUnit.NANOSECONDS), equalTo(103));
        assertThat(integerDeque.pollLast(1, TimeUnit.MINUTES),  equalTo(85));

    }
}
