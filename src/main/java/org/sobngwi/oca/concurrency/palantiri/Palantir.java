package org.sobngwi.oca.concurrency.palantiri;

/**
 * Defines an interface for gazing into a Palantir and plays the role
 * of a "Command" in the Command pattern.
 */
public interface Palantir {
    /**
     * Gaze into the Palantir (and go into a trance ;-)).
     */
     void gaze() throws InterruptedException;

    /**
     * The amount of time the gazing will occur.
     */
    long gazeDuration();

    /**
     * Return the id of the Palantir.
     */
    int getId();
}
