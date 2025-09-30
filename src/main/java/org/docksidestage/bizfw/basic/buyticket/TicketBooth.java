/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

// TODO kumo javadocにauthor追加を by jflute (2025/09/30)
/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // -----------------------------------------------------------------------------------
    // 旧ハードコード定義 (参照用としてコメントアウトで残す)
    // private static final int MAX_ONE_DAY_QUANTITY = 10;
    // private static final int MAX_TWO_DAY_QUANTITY = 20;
    // private static final int MAX_FOUR_DAY_QUANTITY = 40;
    // private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    // private static final int TWO_DAY_PRICE = 13200; // when 2025/09/10
    // private static final int FOUR_DAY_PRICE = 22400; // when 2025/09/10
    // -----------------------------------------------------------------------------------
    // 新実装: TicketType ベースでスケーラブルに管理
    // price や days, default inventory は TicketType が保持

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // private int oneDayQuantity = MAX_ONE_DAY_QUANTITY;
    // private int twoDayQuantity = MAX_TWO_DAY_QUANTITY;
    // private int fourDayQuantity = MAX_FOUR_DAY_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase (仕様維持)

    // #1on1: mutable な Quantityクラスを作るというやり方もある。Mapと両方やることもできる // (2025/09/30)
    //private final Quantity oneDayQuantity = 10;
    //private final Quantity twoDayQuantity = 10;
    
    // #1on1: 一方で、このMapで解決してるやり方はとてもGood。 (2025/09/30)
    // 在庫管理: 種別ごと
    private final java.util.Map<TicketType, Integer> inventoryMap = new java.util.EnumMap<>(TicketType.class);

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        // Enum をループしてデフォルト在庫を初期化
        for (TicketType type : TicketType.values()) {
            inventoryMap.put(type, type.getDefaultInventory());
        }
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // /**
    // * 1Dayパスポートを買う、パークゲスト用のメソッド。
    // * @param handedMoney パークゲストから手渡しされたお金(金額) (NotNull, NotMinus)
    // * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
    // * @throws TicketShortMoneyException 買うのに金額が足りなかったら
    // */
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney The money (amount) handed over from park guest. (NotNull, NotMinus)
     * @return The ticket purchased.
     * @throws TicketSoldOutException When ticket in booth is sold out.
     * @throws TicketShortMoneyException When the specified money is short for purchase.
     */
    public Ticket buyOneDayPassport(Integer handedMoney) {
        // #1on1: 再利用ジレンマ、ちょっとのムダを許容して統一させるか？律儀に最低限の再利用にするか？ (2025/09/30)
        return doBuyOneDay(handedMoney);
    }

    // TODO kumo change の計算をprivateで再利用してみましょう by jflute (2025/09/30)
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        Ticket ticket = doBuy(TicketType.TWO_DAY, handedMoney);
        int change = handedMoney - ticket.getDisplayPrice();
        return new TicketBuyResult(ticket, change);
    }

    public TicketBuyResult buyFourDayPassport(int handedMoney) {
        Ticket ticket = doBuy(TicketType.FOUR_DAY, handedMoney);
        int change = handedMoney - ticket.getDisplayPrice();
        return new TicketBuyResult(ticket, change);
    }

    public TicketBuyResult buyNightOnlyTwoDayPassport(int handedMoney) {
        Ticket ticket = doBuy(TicketType.NIGHT_ONLY_TWO_DAY, handedMoney);
        int change = handedMoney - ticket.getDisplayPrice();
        return new TicketBuyResult(ticket, change);
    }

    // 内部共通 (OneDay は既存シグネチャ互換のため戻り値 Ticket のみ)
    private Ticket doBuyOneDay(int handedMoney) {
        return doBuy(TicketType.ONE_DAY, handedMoney);
    }

    private Ticket doBuy(TicketType type, int handedMoney) {
        validateTicketPurchase(type, handedMoney);
        reduceInventory(type);
        updateSalesProceeds(type.getPrice());
        return new Ticket(type);
    }

    private void validateTicketPurchase(TicketType type, int handedMoney) {
        // 在庫確認
        int current = inventoryMap.get(type);
        if (current <= 0) {
            throw new TicketSoldOutException("Sold out: type=" + type);
        }
        if (handedMoney < type.getPrice()) {
            throw new TicketShortMoneyException("Short money: type=" + type + " money=" + handedMoney);
        }
    }

    private void reduceInventory(TicketType type) {
        inventoryMap.put(type, inventoryMap.get(type) - 1);
    }

    private void updateSalesProceeds(int passportPrice) {
        salesProceeds = (salesProceeds != null ? salesProceeds : 0) + passportPrice;
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() { // Step06でも使用していたのでStep05用はオーバーロードで残す
        return inventoryMap.get(TicketType.ONE_DAY);
    }

    // オーバーロード機能
    // 引数のデフォルト値を設定できない代わりに同じ名前で引数の異なるメソッドを定義できる機能
    // これにより、引数の有無でメソッドを使い分けること可能となる
    // 今回は、引数なしで呼び出すと1日券の数量を取得し、引数にtrueを渡すと2日券の数量を取得する
    // なぜ、これをするのか→1日券と2日券の数量を同じメソッド名で取得できるようにするため。2日目の実装した際に1日目の実装を変えたくないため。
    // この実装は削除→チケットタイプで取得するように変更
    // public int getQuantity(int remainingDays) {
    //     if (remainingDays == 2) {
    //         return twoDayQuantity;
    //     }
    //     if (remainingDays == 4) {
    //         return fourDayQuantity;
    //     }
    //     return oneDayQuantity;
    // }
    public int getQuantity(TicketType type) {
        return inventoryMap.get(type);
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
