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

import java.time.LocalTime; // Night判定用

/**
 * @author jflute
 * @author Kumoshita-a
 */
public class Ticket {
    // TODO done static はインスタンス変数(Attribute)よりも上に定義するのがjavaの慣習なので移動をお願いします by jflute (2025/09/30)
    // Night判定用定数 (暫定仕様: 17:00以降を夜とみなす。終了境界は日付変更前まで)
    // 将来的には (start, end) を TimeSlot 側に保持したり、設定ファイル化する案もある。
    private static final LocalTime NIGHT_START = LocalTime.of(17, 0);
    // private static final LocalTime NIGHT_END = LocalTime.of(23, 59); // 今回は明示的に未使用 (終端扱い)
    // テスト用固定時刻 (null の場合は LocalTime.now())
    private static LocalTime fixedNowForTest;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------------------------------------
    // 旧実装フィールド (参照用としてコメントアウトし保持)
    // private final int displayPrice; // written on ticket, park guest can watch this
    // private boolean alreadyIn; // true means this ticket is unavailable
    // private int remainingDays = 1; // how many days remaining, e.g. 1 or 2
    // -----------------------------------------------------------------------------------
    // 新実装フィールド
    private final TicketType ticketType; // 種別 (価格や日数などを内部に保持)
    private int remainingDays; // 残利用可能日数 (0になったら利用不可)
    private boolean inside; // 現在パーク内にいるかどうか (enter()でtrue、exit()でfalse)

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // #1on1: 全体的にコメントが素晴らしい (2025/09/30)
    // _/_/_/_/_/_/_/_/
    // 旧コンストラクタ互換: OneDay想定
    public Ticket(int displayPrice) { // 既存テスト互換のため残す
        // ONE_DAY相当として扱う (将来 displayPrice が ONE_DAY と異なるケースが来たら廃止検討)
        this.ticketType = TicketType.ONE_DAY; // 価格ズレ許容 (再設計時に調整可)
        this.remainingDays = 1;
    }

    // 旧コンストラクタ互換: 任意日数
    public Ticket(int displayPrice, int remainingDays) { // 既存テスト互換のため残す
        this.ticketType = remainingDays == 2 ? TicketType.TWO_DAY : remainingDays == 4 ? TicketType.FOUR_DAY : TicketType.ONE_DAY; // 簡易マッピング
        this.remainingDays = remainingDays;
    }

    // 新しい正式コンストラクタ
    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;
        this.remainingDays = ticketType.getDays();
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    // #1on1: alreadyInの扱いについて、まだちょっと悩み中 (2025/09/19)
    // 旧API互換: doInPark() は1回の enter を表現する。複数日券は remainingDays を消費しつつ再入場可能。
    public void doInPark() {
        // --- NightOnly 判定追加 (大きめの仕様変更なので旧ロジックはコメント保持) ---
        // 旧ロジック:
        // if (remainingDays <= 0) {
        //     throw new IllegalStateException("利用可能日数が残っていません: type=" + ticketType);
        // }
        // remainingDays--; inside = true;
        // --------------------------------------------------------------

        // 1. 残日数チェック
        if (remainingDays <= 0) {
            throw new IllegalStateException("利用可能日数が残っていません: type=" + ticketType);
        }
        // #1on1: 本来は、TimeSlotで詳しい時間帯を管理して、すべての時間帯に対してenumだけで解決できるように by くもしたさん
        // 現状は、そこまでやる必要はないということで、以下のようになっている。
        // 2. 夜専用チケットの場合の時間帯チェック
        if (ticketType.getTimeSlot() == TimeSlot.NIGHT_ONLY) {
            LocalTime now = fixedNowForTest != null ? fixedNowForTest : LocalTime.now();
            if (!isNight(now)) { // 夜でなければ利用不可
                throw new IllegalStateException("夜間専用チケットは夜時間帯のみ利用可能 (" + NIGHT_START + "以降)。 now=" + now + " type=" + ticketType);
            }
        }
        // 3. 日数消費と入場状態更新
        remainingDays--; // 日数消費 (注: 実際は日付ごとに消費したいなら LocalDate トラッキングが必要)
        inside = true;
    }

    /**
     * パークに入場する。残日数を1消費し inside を true にする。
     */
    public void enter() {
        doInPark();
    }

    /**
     * パークから退場する。複数回呼んでも問題ない(冪等)。
     */
    public void exit() {
        inside = false;
    }

    // TODO done kumo @return を追加を。こういうメソッドは、逆に説明省略で、@returnだけでもOK by jflute (2025/09/30)
    /**
     * @return 現在パーク内にいるかどうか。
     */
    public boolean isInside() {
        return inside;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() { // 旧API互換: displayPrice を ticketType から算出
        return ticketType.getPrice();
    }

    public boolean isAlreadyIn() { // 旧フィールド alreadyIn 互換
        return inside;
    }

    public boolean isTwoDayPassport() { // 判定仕様を type に基づいて行う
        // TODO done kumo 昼間オンリーTwoDayが追加されたとき、ここも追加しないといけないのがもったいない by jflute (2025/09/30)
        // なんちゃTwoDayが全部trueになるのであれば、getDays() == 2 でもいいのかも!?
        // 採用しました！（一旦２日入れることが可能なチケットとして要件定義する）
        return ticketType.getDays() == 2;
    }

    // 新規補助メソッド
    public int getRemainingDays() {
        return remainingDays;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    // ===================================================================================
    //                                                                       Night Utility
    //                                                                       =============
    /**
     * 現在時刻が夜時間帯かどうかを判定する簡易メソッド。
     * <p>暫定仕様: 17:00 以上を夜とみなす。終端は日付変更まで(= LocalTime の自然上限)。</p>
     * @param now 判定対象時刻 (NotNull)
     * @return 夜なら true
     */
    private boolean isNight(LocalTime now) {
        return !now.isBefore(NIGHT_START); // now >= NIGHT_START
    }

    // ===================================================================================
    //                                                                      Test Utility
    //                                                                      ============
    // #1on1: 手軽な方法として、意外に現実的 (大きな仕組みを作らずサクッと) (2025/09/30)
    // ちょっと危ないかもしれないけど、目立つメソッド名で (社内だけで使うものとか前提であれば)
    // (オブジェクト指向的にやるとしたら、TicketBoothを継承してmockをつくったりなどなど: 思考トレーニング)
    // #1on1: LastaFlute の shootBowgunCurrentTimeProvider() を追ってみた
    /**
     * (テスト専用) 現在時刻を固定して Night 判定を制御する。null を渡すとリセット。
     * @param time 固定する時刻 (null 可)
     */
    public static void __testFixCurrentTime(LocalTime time) {
        fixedNowForTest = time;
    }
}
