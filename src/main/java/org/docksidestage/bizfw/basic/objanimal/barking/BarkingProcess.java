package org.docksidestage.bizfw.basic.objanimal.barking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The process for barking.
 * @author Kumoshita-a
 */
public class BarkingProcess {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound executeBark(BarkingCallback callback) {
        breatheIn(callback);
        prepareAbdominalMuscle(callback);
        String barkWord = callback.getBarkWord();
        BarkedSound barkedSound = doBark(callback, barkWord);
        return barkedSound;
    }

    protected void breatheIn(BarkingCallback callback) {
        logger.debug("...Breathing in for barking");
        callback.downHitPoint();
        callback.afterBreatheIn();
    }

    protected void prepareAbdominalMuscle(BarkingCallback callback) {
        logger.debug("...Using my abdominal muscle for barking");
        callback.downHitPoint();
    }

    protected BarkedSound doBark(BarkingCallback callback, String barkWord) {
        callback.downHitPoint();
        return new BarkedSound(barkWord);
    }
}
