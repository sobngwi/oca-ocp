package org.sobngwi.oca.concurrency.pingpong.good;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sobngwi.oca.functional.ThreadPoolSupplier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

public class PlayPingPongTest extends AbstractPlayPingPong {
    ExecutorService executorService;

    @Before
    public void setUp() throws Exception {
        strategyPltfinstance =  PlatformStrategy.instance
                (new PlatformStrategyFactory
                        (System.out).makePlatformStrategy());
        Options.getOptionsInstance().parseArgs(new String[]{"-s", "4"});

    }

    @After
    public void tearDown() throws Exception {
       /* executorService.shutdown();
        if ( ! executorService.awaitTermination(1, TimeUnit.NANOSECONDS))
           executorService.shutdownNow();*/

    }

    @Test
    public void mocksAreUp() {
        //assertNotNull(playPingPongMock);
        //assertNotNull(pingPongThreadBlockingQueueMock);

    }

    @Test
    public void playPingPong_blockingQueue() throws Exception {

        executorService =  Executors.newFixedThreadPool(10);
        executorService
                    .submit(playPingPongWithQueu, "OK").get();

           assertThat(strategyPltfinstance.getOutPutresult().size(), equalTo(2));
           assertThat(strategyPltfinstance.getOutPutresult().toString(),allOf(containsString("ping(1)"),
                   containsString("pong(1)")));

        executorService.shutdown();
        if ( ! executorService.awaitTermination(1, TimeUnit.NANOSECONDS))
            executorService.shutdownNow();
    }

    @Test
    public void play1_000_000_PingPongs_blockingQueue() throws Exception {

        executorService =  Executors.newFixedThreadPool(10);
        executorService
                .submit(play1_000PingPongWithQueu, "OK").get();

        assertThat(strategyPltfinstance.getOutPutresult().size(), equalTo(2_000));
        assertThat(strategyPltfinstance.getOutPutresult().toString(),allOf(containsString("ping(1)"),
                containsString("pong(1)")));

        executorService.shutdown();
        if ( ! executorService.awaitTermination(1, TimeUnit.NANOSECONDS))
            executorService.shutdownNow();
    }
}