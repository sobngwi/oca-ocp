package org.sobngwi.oca.concurrency.deadlock;

import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class DeadLockTest {

    private Object lock1, lock2;
    private Logger log = Logger.getLogger(DeadLockTest.class.getName());

    @Before
    public void setUp() {
        lock1 = new Object();
        lock2 = new Object();
    }

    private final Thread thread1 = new Thread() {
        public void run() {
            synchronized (lock1) {
                log.info("thread1 holds lock1");
                try {
                    Thread.sleep(2);
                } catch (Exception e) {
                    log.info(e.toString());
                }
                log.info("thread1 waiting for lock2");
                log.info("(thread1 needs lock2 to release lock1...)");
                synchronized (lock2) {
                    log.info("thread1 holds lock1 and lock2");
                } // lock2 released here
            } // lock1 released here
        }
    };
    private final Thread thread2 = new Thread() {
        public void run() {
            synchronized (lock2) {
                log.info("thread2 holds lock2");
                try {
                    Thread.sleep(2);
                } catch (Exception e) {
                    log.info(e.toString());
                }
                log.info("(thread2 waiting for lock1");
                log.info("(thread2 needs lock1 to release lock2...)");
                synchronized (lock1) {
                    log.info("thread2 holds lock2 and lock1");
                } // lock1 released here
            } // lock2 released here
        }
    };


    @Test
    public void shouldBlockOndeadLock() throws InterruptedException {

        thread1.start();
        thread2.start();

        assertThat(true, equalTo(thread1.isAlive()));
        assertThat(true, equalTo(thread1.isAlive()));

        thread1.join(3000);
        thread2.join(2000);
        assertThat(true, equalTo(thread1.isAlive()));
        assertThat(true, equalTo(thread1.isAlive()));
    }
}
