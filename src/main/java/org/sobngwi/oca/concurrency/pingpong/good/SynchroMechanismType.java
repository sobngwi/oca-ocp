package org.sobngwi.oca.concurrency.pingpong.good;


public  enum SynchroMechanismType {
    SEMA(1), MONOBJ(2), COND(3), QUEUE(4);
    private final int synchType;

    SynchroMechanismType(int synchType) {
        this.synchType = synchType;
    }
}