package org.docksidestage.bizfw.basic.objanimal.barking;

/**
 * Callback interface for barking process.
 * @author Kumoshita-a
 */
public interface BarkingCallback {

    /**
     * Get the bark word of the animal.
     * @return the bark word (e.g., "wan" for Dog, "nya-" for Cat)
     */
    String getBarkWord();

    /**
     * Decrease the hit point of the animal.
     */
    void downHitPoint();

    /**
     * Hook method called after breathing in.
     * (e.g., Zombie uses this to count breathe-ins)
     */
    void afterBreatheIn();
}
