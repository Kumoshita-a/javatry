package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
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
    public BarkedSound executeBark(Animal animal) {
        breatheIn(animal);
        prepareAbdominalMuscle(animal);
        String barkWord = animal.getBarkWord();
        BarkedSound barkedSound = doBark(animal, barkWord);
        return barkedSound;
    }

    protected void breatheIn(Animal animal) {
        logger.debug("...Breathing in for barking");
        animal.downHitPoint();
        animal.hookAfterBreatheIn();
    }

    protected void prepareAbdominalMuscle(Animal animal) {
        logger.debug("...Using my abdominal muscle for barking");
        animal.downHitPoint();
    }

    protected BarkedSound doBark(Animal animal, String barkWord) {
        animal.downHitPoint();
        return new BarkedSound(barkWord);
    }
}
