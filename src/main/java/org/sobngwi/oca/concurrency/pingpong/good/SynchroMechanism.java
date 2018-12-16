package org.sobngwi.oca.concurrency.pingpong.good;


public  enum SynchroMechanism {
    SEMA(1), MONOBJ(2), COND(3), QUEUE(4);
    private final int synchType;

    SynchroMechanism(int synchType) {
        this.synchType = synchType;
    }
}