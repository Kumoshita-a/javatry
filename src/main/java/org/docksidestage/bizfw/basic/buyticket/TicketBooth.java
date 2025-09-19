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

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_ONE_DAY_QUANTITY = 10;
    private static final int MAX_TWO_DAY_QUANTITY = 20;
    private static final int MAX_FOUR_DAY_QUANTITY = 40;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200; // when 2025/09/10
    private static final int FOUR_DAY_PRICE = 22400; // when 2025/09/10

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int oneDayQuantity = MAX_ONE_DAY_QUANTITY;
    private int twoDayQuantity = MAX_TWO_DAY_QUANTITY;
    private int fourDayQuantity = MAX_FOUR_DAY_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
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
        // TODO kumoshita 修行++: 在庫を分離した状態で、でも流れも再利用したいところ by jflute (2025/09/19)
        // TODO kumoshita [いいね] メソッド名、シンプルでわかりやすい by jflute (2025/09/19)
        validateTicketPurchase(handedMoney);
        --oneDayQuantity;
        updateSalesProceeds(ONE_DAY_PRICE);
        return new Ticket(ONE_DAY_PRICE);
    }

    private void validateTicketPurchase(Integer handedMoney) {
        // #1on1: 順番違いのバグは、細かく分けるアーキテクチャとかになってくると、さらにわかりづらくなってくる
        if (oneDayQuantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney < ONE_DAY_PRICE) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
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
    public int getQuantity() {
        return oneDayQuantity;
    }

    // オーバーロード機能
    // 引数のデフォルト値を設定できない代わりに同じ名前で引数の異なるメソッドを定義できる機能
    // これにより、引数の有無でメソッドを使い分けること可能となる
    // 今回は、引数なしで呼び出すと1日券の数量を取得し、引数にtrueを渡すと2日券の数量を取得する
    // なぜ、これをするのか→1日券と2日券の数量を同じメソッド名で取得できるようにするため。2日目の実装した際に1日目の実装を変えたくないため。
    public int getQuantity(int remainingDays) {
        if (remainingDays == 2) {
            return twoDayQuantity;
        }
        if (remainingDays == 4) {
            return fourDayQuantity;
        }
        return oneDayQuantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }

    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        // 基本的なロジックはbuyOneDayPassportと同じ
        validateTicketPurchase(handedMoney);
        --twoDayQuantity;
        updateSalesProceeds(TWO_DAY_PRICE);
        Ticket ticket = new Ticket(TWO_DAY_PRICE, 2);
        int change = handedMoney - TWO_DAY_PRICE;
        return new TicketBuyResult(ticket, change);
    }

    private void updateSalesProceeds(int passportPrice) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + passportPrice;
        } else {
            salesProceeds = passportPrice;
        }
    }

    public TicketBuyResult buyFourDayPassport(int handedMoney) {
        validateTicketPurchase(handedMoney);
        --fourDayQuantity;
        updateSalesProceeds(FOUR_DAY_PRICE);
        Ticket ticket = new Ticket(FOUR_DAY_PRICE, 4);
        int change = handedMoney - FOUR_DAY_PRICE;
        return new TicketBuyResult(ticket, change);
    }
}
