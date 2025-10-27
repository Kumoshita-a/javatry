package org.docksidestage.javatry.basic.st6.os;

/**
 * The class for Windows operation system.
 * @author Kumoshita-a
 */
public class St6WindowsOperationSystem extends St6OperationSystem {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public St6WindowsOperationSystem(String loginId) {
        super("Windows", loginId);
    }

    // ===================================================================================
    //                                                                      User Directory
    //                                                                      ==============
    @Override
    protected String getFileSeparator() {
        return "\\";
    }

    @Override
    protected String getUserDirectory() {
        return "/Users/" + getLoginId();
    }
}
