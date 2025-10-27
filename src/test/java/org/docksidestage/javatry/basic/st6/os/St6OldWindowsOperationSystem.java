package org.docksidestage.javatry.basic.st6.os;

/**
 * The class for old Windows operation system (XP or earlier).
 * @author Kumoshita-a
 */
public class St6OldWindowsOperationSystem extends St6OperationSystem {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public St6OldWindowsOperationSystem(String loginId) {
        super("OldWindows", loginId);
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
        return "/Documents and Settings/" + getLoginId();
    }
}
