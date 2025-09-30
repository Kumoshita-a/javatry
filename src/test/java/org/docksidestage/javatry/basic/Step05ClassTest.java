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
package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.bizfw.basic.buyticket.TicketBuyResult;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 * 
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 * 
 * @author jflute
 * @author Kumoshita-a
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(7400);
        int sea = booth.getQuantity();
        log(sea); // your answer? => 9
        // boothはTicketBoothのインスタンスであり、quantityは初期値はQUANTITY_MAXの10である。
        // buyOneDayPassportメソッドを呼び出すと、quantityが1減るので、getQuantityメソッドを呼び出すと9が返される。
        // したがって、最終的にseaには9が代入される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 10000
        // boothはTicketBoothのインスタンスであり、salesProceedsは初期値はnullである。
        // buyOneDayPassportメソッドを呼び出すと、salesProceedsはnullなので引数の10000がそのまま代入される。
        // したがって、getSalesProceedsメソッドを呼び出すと10000が返される。
        // したがって、最終的にseaには10000が代入される。
        // 修正後 -> 7400
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth();
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => null
        // boothはTicketBoothのインスタンスであり、salesProceedsは初期値はnullである。
        // getSalesProceedsメソッドを呼び出すと、salesProceedsがnullのままなのでnullが返される。
        // したがって、最終的にseaにはnullが代入される
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 9
        // doTest_class_ticket_wrongQuantityメソッドを呼び出すと、TicketBoothのインスタンスが生成される。
        // その後、buyOneDayPassportメソッドを呼び出すと、まずquantityが1減り、handedMoneyが7399であり、ONE_DAY_PRICEの7400に満たないため、TicketShortMoneyExceptionがスローされる。
        // したがって、getQuantityメソッドを呼び出すと、quantityの９が返される。
        // したがって、最終的にseaには9が代入される。
        // 修正後 -> 10
    }

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 7399;
        try {
            booth.buyOneDayPassport(handedMoney);
            fail("always exception but none");
        } catch (TicketShortMoneyException continued) {
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getQuantity();
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log("quantity=" + sea); // should be max quantity, visual check here　-> 10
    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        // #1on1: 違うint + 違うint をそもそも足せないようにしたい話 (DDDのvalue objectなど)
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log("salesProceeds=" + sea); // should be same as one-day price, visual check here　-> 7400
    }

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // uncomment after making the method
        TicketBooth booth = new TicketBooth();
        int money = 14000;
        TicketBuyResult result = booth.buyTwoDayPassport(money);
        Ticket ticket = result.getTicket();
        Integer sea = booth.getSalesProceeds() + result.getChange();
        log("salesProceeds=" + sea); // should be same as money -> 14000

        // and show two-day passport quantity here
        log("twoDayQuantity=" + booth.getQuantity(ticket.getTicketType())); // should be max quantity -1 -> 19
    }

    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        log(booth.getQuantity(), booth.getSalesProceeds()); // should be same as before-fix
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // uncomment out after modifying the method
        TicketBooth booth = new TicketBooth();
        Ticket oneDayPassport = booth.buyOneDayPassport(10000);
        log(oneDayPassport.getDisplayPrice()); // should be same as one-day price -> 7400
        log(oneDayPassport.isAlreadyIn()); // should be false
        oneDayPassport.doInPark();
        log(oneDayPassport.isAlreadyIn()); // should be true
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
        // uncomment after modifying the method
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(twoDayPassport.getDisplayPrice() + change); // should be same as money -> 20000
    }

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        TicketBuyResult buyResult = booth.buyTwoDayPassport(14000);
        Ticket twoDayPassport = buyResult.getTicket();
        log(twoDayPassport.getDisplayPrice()); // should be same as two-day price -> 13200
        log(twoDayPassport.isAlreadyIn()); // should be false
        twoDayPassport.doInPark();
        log(twoDayPassport.isAlreadyIn()); // should be true
        twoDayPassport.doInPark();
        log(twoDayPassport.isAlreadyIn()); // should be true
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        // uncomment when you implement this exercise
        TicketBooth booth = new TicketBooth();
        Ticket oneDayPassport = booth.buyOneDayPassport(14000);
        showTicketIfNeeds(oneDayPassport); // other
        TicketBuyResult buyResult = booth.buyTwoDayPassport(14000);
        Ticket twoDayPassport = buyResult.getTicket();
        showTicketIfNeeds(twoDayPassport); // two-day passport
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
        // #1on1: 雲下さんの頭の中で考えているうーんを実装せざるを得ないかも!? (2025/09/19)
        if (ticket.isTwoDayPassport()) { // write determination for two-day passport
            log("two-day passport");
        } else {
            log("other");
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder_four() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        int money = 23000;
        TicketBuyResult buyResult = booth.buyFourDayPassport(money);
        Ticket fourDayPassport = buyResult.getTicket();
        log(fourDayPassport.getDisplayPrice()); // should be same as four-day price -> 22400
        int change = buyResult.getChange();
        Integer sea = booth.getSalesProceeds() + change;
        log(sea); // should be same as money -> 23000
        log(fourDayPassport.isAlreadyIn()); // should be false
        fourDayPassport.doInPark();
        log(fourDayPassport.isAlreadyIn()); // should be true
        fourDayPassport.doInPark();
        log(fourDayPassport.isAlreadyIn()); // should be true
        fourDayPassport.doInPark();
        log(fourDayPassport.isAlreadyIn()); // should be true
        fourDayPassport.doInPark();
        log(fourDayPassport.isAlreadyIn()); // should be true
    }

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        // 指定した複数時刻で NightOnlyTwoDayPassport の挙動を検証
        // 17:00 未満 -> 失敗, 17:00 以降 -> 成功 となるかを確認する
        java.time.LocalTime[] patternTimes = { java.time.LocalTime.of(16, 59), java.time.LocalTime.of(17, 0),
                java.time.LocalTime.of(21, 30) };

        for (java.time.LocalTime time : patternTimes) {
            log("-- time pattern: " + time + " --");
            Ticket.__testFixCurrentTime(time); // テスト用固定
            TicketBooth booth = new TicketBooth();
            int money = 10000;
            TicketBuyResult buyResult = booth.buyNightOnlyTwoDayPassport(money);
            Ticket nightOnly = buyResult.getTicket();
            log(nightOnly.getDisplayPrice()); // 7400 期待
            int change = buyResult.getChange();
            Integer sea = booth.getSalesProceeds() + change;
            log(sea); // 10000 期待
            boolean entered = false;
            try {
                nightOnly.doInPark();
                entered = true;
            } catch (IllegalStateException e) {
                log("(expected if day) cannot enter: " + e.getMessage());
            }
            // アサート (JDK8 古い JUnit なのでシンプルに条件分岐)
            if (time.isBefore(java.time.LocalTime.of(17, 0))) {
                assertFalse("16:59 は入場できない想定", entered);
            } else {
                assertTrue(time + " は入場できる想定", entered);
            }
        }
        // リセット
        Ticket.__testFixCurrentTime(null);
    }

    /**
     * Refactor if you want to fix (e.g. is it well-balanced name of method and variable?). <br>
     * (その他、気になるところがあったらリファクタリングしてみましょう (例えば、バランスの良いメソッド名や変数名になっていますか？))
     */
    public void test_class_moreFix_yourRefactoring() {
        // your confirmation code here
    }

    // #1on1: コメントを書こうと思えばしっかり書ける人だからこそ、省略できる。
    // NxBatchRecorder のコードを眺めて参考に。
    // (余談、コピペ前提OSSのクラス)
    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // your confirmation code here
    }

    // ===================================================================================
    //                                                                         Devil Stage
    //                                                                         ===========
    /**
     * If your specification is to share inventory (quantity) between OneDay/TwoDay/...,
     * change the specification to separate inventory for each OneDay/TwoDay/.... <br>
     * (もし、OneDay/TwoDay/...で在庫(quantity)を共有する仕様になってたら、
     * OneDay/TwoDay/...ごとに在庫を分ける仕様に変えてみましょう)
     */
    public void test_class_moreFix_zonedQuantity() {
        // your confirmation code here
    }
}
