package org.sobngwi.oca.concurrency.pingpong.good;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.sobngwi.oca.functional.Logs.DoLog;
import org.sobngwi.oca.functional.ThreadPoolSupplier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class AbstractPlayPingPong {

    protected PlatformStrategy strategyPltfinstance ;

    protected PlayPingPong playPingPongWithQueu = new PlayPingPong(1, SynchroMechanismType.QUEUE);
    protected PlayPingPong play1_000PingPongWithQueu = new PlayPingPong(1000, SynchroMechanismType.QUEUE);

    @Before
    public void setUp() throws Exception {

    }
}
