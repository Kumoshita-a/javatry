package org.docksidestage.javatry.basic.st6.dbms;

/**
 * The abstract class for database management system.
 * @author Kumoshita-a
 */
public abstract class St6Database {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected String url;
    protected String user;
    protected String password;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public St6Database(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // ===================================================================================
    //                                                                              Paging
    //                                                                              ======
    /**
     * ページクエリの作成
     * @param pageSize ページサイズ.
     * @param pageNumber ページ番号 (1-origin).
     * @return ページクエリ文字列.
     */
    public String getPagingQuery(int pageSize, int pageNumber) {
        return buildPagingQuery(pageSize, pageNumber);
    }

    /**
     * ページクエリの構築 (サブクラスで実装)
     * @param pageSize ページサイズ.
     * @param pageNumber ページ番号 (1-origin).
     * @return ページクエリ文字列.
     */
    protected abstract String buildPagingQuery(int pageSize, int pageNumber);

    /**
     * オフセットの計算 (共通処理)
     * @param pageSize ページサイズ.
     * @param pageNumber ページ番号 (1-origin).
     * @return オフセット値.
     */
    protected int calculateOffset(int pageSize, int pageNumber) {
        return pageSize * (pageNumber - 1);
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }
}
