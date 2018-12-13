package org.sobngwi.oca.concurrency.single;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class ZooInfoTest {

    private List<Integer> arrayListResult;
    private ExecutorService singleExecutorService;
    final int maxSize = 1_000_000;

    static <E> Optional<E> wrapException(Future<E> future) {

        try {
            return Optional.of(future.get(1, TimeUnit.NANOSECONDS));
        } catch (Throwable t) {
            System.err.println(t.toString());
            return Optional.empty();
        }
    }

    @Before
    public void setUp() throws Exception {
        arrayListResult = new ArrayList<>();
        singleExecutorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void singleThreadExecutor_WithExcuteMethode() {


        IntStream.iterate(0, i -> i + 1)
                .limit(maxSize)
                .forEach(x ->
                        singleExecutorService.execute(() -> arrayListResult.add(x)));

        singleExecutorService.shutdown();

        try {
            singleExecutorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail();
        } finally {
            if (singleExecutorService != null) singleExecutorService.shutdownNow();
        }

        assertThat(arrayListResult.size(), equalTo(maxSize));
        assertThat(arrayListResult.get(0), equalTo(0));
        assertThat(arrayListResult.get(maxSize / 2), equalTo(maxSize / 2));
        assertThat(arrayListResult.get(maxSize - 1), equalTo(maxSize - 1));

        for (int i = 0; i < maxSize; i++) {
            if (arrayListResult.get(i) == i) {
                continue;
            } else
                fail();
        }

    }


    @Test
    @Ignore
    public void singleThreadExecutor_WithSubmitMethode() {


        IntStream.iterate(0, i -> i + 1)
                .limit(maxSize)
                .forEach(x -> {
                    Future<?> result =
                            singleExecutorService.submit(() -> arrayListResult.add(x));
                    wrapException(result);
                });

        assertThat(singleExecutorService.shutdownNow().size(), equalTo(0));
        assertThat(arrayListResult.size(), equalTo(maxSize));
        assertThat(arrayListResult.get(0), equalTo(0));
        assertThat(arrayListResult.get(maxSize / 2), equalTo(maxSize / 2));
        assertThat(arrayListResult.get(maxSize - 1), equalTo(maxSize - 1));

        for (int i = 0; i < maxSize; i++) {
            if (arrayListResult.get(i) == i) {
                continue;
            } else
                fail();
        }
    }

    @Test
    public void singleThreadExecutor_WithCallable_RunnableMethod() {

        IntStream.iterate(0, i -> i + 1)
                .limit(maxSize)
                .forEach(x -> {
                            singleExecutorService.submit(() -> { arrayListResult.add(x);return x; }); // Callable
                            singleExecutorService.submit(() -> System.out.print(""), x); // Runnable, T
                            singleExecutorService.submit(() -> System.out.print(""));     //Runnable
                        }

                );

        try {
            singleExecutorService.awaitTermination(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            fail();
        }

        assertThat(singleExecutorService.shutdownNow().size(), equalTo(0));
        assertThat(arrayListResult.size(), equalTo(maxSize));
        assertThat(arrayListResult.get(0), equalTo(0));
        assertThat(arrayListResult.get(maxSize / 2), equalTo(maxSize / 2));
        assertThat(arrayListResult.get(maxSize - 1), equalTo(maxSize - 1));

        for (int i = 0; i < maxSize; i++) {
            if (arrayListResult.get(i) == i) {
                continue;
            } else
                fail();
        }

    }


    @Test
    public void shutdownNowExecutorServiceWithExecuteMethode() {

        IntStream.iterate(0, i -> i + 1)
                .limit(maxSize)
                .forEach(x ->
                        singleExecutorService.execute(() -> arrayListResult.add(x)));
        assertThat(singleExecutorService.shutdownNow().size(), not(equalTo(0)));
    }

    @Test
    public void shutdownNowExecutorServiceWithSubmitMethode() {

        IntStream.iterate(0, i -> i + 1)
                .limit(maxSize)
                .forEach(x ->
                        singleExecutorService.submit(() -> arrayListResult.add(x)));
        assertThat(singleExecutorService.shutdownNow().size(), not(equalTo(0)));
    }

    @Test(expected = RejectedExecutionException.class)
    public void shouldThrowRejectedExecutionException_asSeerviceIsShutingDown() {
        IntStream.iterate(0, i -> i + 1)
                .limit(maxSize)
                .forEach(x ->
                        singleExecutorService.execute(() -> arrayListResult.add(x)));

        singleExecutorService.shutdown();
        singleExecutorService.execute(() -> System.out.println(" Throw a RejectedExceutionException"));
        if (singleExecutorService != null) singleExecutorService.shutdownNow();

    }

}
