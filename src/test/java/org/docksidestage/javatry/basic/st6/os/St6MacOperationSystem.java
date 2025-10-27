package org.docksidestage.javatry.basic.st6.os;

/**
 * The class for Mac operation system.
 * @author Kumoshita-a
 */
public class St6MacOperationSystem extends St6OperationSystem {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public St6MacOperationSystem(String loginId) {
        super("Mac", loginId);
    }
    
    // ===================================================================================
    //                                                                      User Directory
    //                                                                      ==============
    @Override
    protected String getFileSeparator() {
        return "/";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + getLoginId();
    }
}
