package org.docksidestage.javatry.basic.st6.dbms;

// #1on1: データベースという言葉、RDBMSという言葉 (2025/11/11)
/**
 * The abstract class for database management system.
 * @author Kumoshita-a
 */
public abstract class St6Database {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // TODO done kumoshita immutableなインスタンス変数なのでfinalを付けてみましょう by jflute (2025/11/11)
    protected final String url;
    protected final String user;
    protected final String password;

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

    // #1on1: origin慣習
    //  index: 0 origin
    //  No, number: 1 origin
    // 書くときは一貫性をぜひ持たせて。でも読むときはバラバラなときもあるので疑うように。
    /**
     * ページクエリの構築 (サブクラスで実装)
     * @param pageSize ページサイズ.
     * @param pageNumber ページ番号 (1-origin).
     * @return ページクエリ文字列.
     */
    protected String buildPagingQuery(int pageSize, int pageNumber) {
        final int offset = calculateOffset(pageSize, pageNumber);
        final String pagingQuery = createQuery(offset, pageSize);
        return pagingQuery;
    }

    /**
     * オフセットの計算 (共通処理)
     * @param pageSize ページサイズ.
     * @param pageNumber ページ番号 (1-origin).
     * @return オフセット値.
     */
    protected int calculateOffset(int pageSize, int pageNumber) {
        return pageSize * (pageNumber - 1);
    }

    /**
     * クエリ文字列の生成 (サブクラスで実装)
     * @param offset オフセット値.
     * @param pageSize ページサイズ.
     * @return クエリ文字列.
     */
    protected abstract String createQuery(int offset, int pageSize);

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
