package org.docksidestage.bizfw.basic.objanimal;

/**
 * The object for elephant(象).
 * @author Kumoshita-a
 */
public class Elephant extends Animal {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Elephant() {
    }

    // ===================================================================================
    //                                                                            Hit Point
    //                                                                            =========
    @Override
    protected int getInitialHitPoint() {
        return 15; // 象は体力がある
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    @Override
    public String getBarkWord() { // public (same as Animal)
        return "pao"; // 象の鳴き声
    }
}
