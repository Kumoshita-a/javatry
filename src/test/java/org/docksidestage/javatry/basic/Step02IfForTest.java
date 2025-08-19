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

import java.util.ArrayList;
import java.util.List;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Kumoshita-a
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001
        // seaは904以上なので、if文の条件式がtrueとなり、2001が代入される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7;
        }
        log(sea); // your answer? => 7
        // seaは904なので、if文の条件式がfalseとなり、elseの処理が実行され、seaには7が代入される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7;
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7
        // seaは904なので、最初のif文の条件式がfalseとなり、次のelseif文の条件式がtrueとなるので、seaには7が代入される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        // (sea, land) = (904, false)
        if (sea > 904) { // <falseなので実行されない>
            sea = 2001;
        } else if (land && sea >= 904) { // <falseなので実行されない>
            sea = 7;
        } else if (sea >= 903 || land) { // <trueなので実行される>
            sea = 8;
            // (sea, land) = (8, false)
            if (!land) { // <trueなので実行される>
                land = true;
                // (sea, land) = (8, true)
            } else if (sea <= 903) {
                sea++;
            }
            // <次のif文へ（つまり以下のelseif文は実行されない）>
        } else if (sea == 8) {
            sea++;
            land = false;
        } else {
            sea = 9;
        }
        if (sea >= 9 || (sea > 7 && sea < 9)) { // <trueなので実行される>
            sea--;
            // (sea, land) = (7, true)
        }
        if (land) { // <trueなので実行される>
            sea = 10;
            // (sea, land) = (10, true)
        }
        log(sea); // your answer? => 10
        // seaは904なので、最初のif文の条件式がfalseとなり、landはfalseなので次のelseif文の条件式（land が true かつ sea が 904 以上）もfalseとなる。
        // また、次のelseif文の条件式（sea が 903 以上 または land が true ）はtrueとなり、seaには8が代入される。
        // その中の処理では、landがfalseなので、if文の中の処理が実行され、landはtrueになる。
        // seaが8より次のif文の条件式（sea が 9 以上 または sea が 7 より大きく 9 より小さい ）はtrueなので、seaは8から1引かれて7になる。
        // landはtrueより最後のif文の条件式（land が true）はtrueなのでseaは10に変更される。
        // よって、最終的にseaは10となる。
        // 言葉で書くと長いのでコードに紐づけると上のコメントになる

        // #1on1: 漠然読みで構造だけまず知って、目的を達成できそうなところをフォーカス読み (2025/08/08)
        // 前回、BigDecimal@add()も同じような読み方をしていた。
        // こういう読み方を意識していると、フレームワークやライブラリのソースコードを読めるようになる。
        // 仮説思考と通じるところあるかも？
        // https://jflute.hatenadiary.jp/entry/20150111/kasetsu
    }

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);
            if (i == 1) {
                sea = stage;
            }
        }
        log(sea); // your answer? => dockside
        // .size()は4なので、iは0から3までの値をとる。
        // iが1のとき、stageListは{"broadway", "dockside", "hangar", "magiclamp"}となり、stageは"dockside"となる。
        // したがって、stageList.get(i)ではi番目の要素が返りstageに代入される。
        // iが1のときにseaにstageを代入する。このとき、stageは"dockside"なので、seaには"dockside"が入る。
        // そのあとfor文の処理は続くが、if文の処理は実行されないので最終的に、seaは"dockside"となる。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            sea = stage;
        }
        log(sea); // your answer? => magiclamp
        // for-each文では、stageListの要素を順番にstageに代入していく。
        // seaに順に要素が代入されるので、最終的にseaには最後の要素の"magiclamp"が入る。
        // #1on1 拡張for文という正式用語は、現場に浸透しているか？普通のfor文とは？ (2025/08/08)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }
        log(sea); // your answer? => hangar
        // for-each文では、stageListの要素を順番にstageに代入していく。
        // まず、stageが"broadway"のとき、if文の条件式(stage.startsWith("br"))がtrueとなるので、continue文により次のループへ移行する。
        // 次に、stageが"dockside"のとき、if文の条件式(stage.startsWith("br"))がfalseとなるので、seaに"dockside"が代入される。
        // 次に、stageが"hangar"のとき、if文の条件式(stage.startsWith("br"))がfalseとなるので、seaに"hangar"が代入される。
        // さらに、if文の条件式(stage.contains("ga"))がtrueとなるので、break文によりループを抜ける。
        // したがって、最終的にseaには"hangar"が入っている。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList();
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> {
            if (sb.length() > 0) {
                return;
            }
            if (stage.contains("i")) {
                sb.append(stage);
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => dockside
        // forEachメソッドでは、stageListの要素を順番にstageに代入していく。
        // まず、stageが"broadway"のとき、sbは初期値で何も入っていないので、if文の条件式(sb.length() > 0)がfalseとなり、次のif文(stage.contains("i"))が実行される。
        // このとき、"broadway"には"i"が含まれていないので、sbには何も追加されない。
        // 次に、stageが"dockside"のとき、if文の条件式(sb.length() > 0)がfalseとなるので、次のif文(stage.contains("i"))が実行される。
        // このとき、"dockside"には"i"が含まれているので、sbに"dockside"が追加される。
        // 次に、stageが"hangar"のとき、if文の条件式(sb.length() > 0)がtrueとなるので、return文により次のループへ移行する。
        // 最後に、stageが"magiclamp"のとき、if文の条件式(sb.length() > 0)がtrueとなるので、return文によりループが終わる。
        // したがって、最終的にsbには"dockside"が入っているので、seaには"dockside"が代入される。

        // #1on1: 拡張for文とforEach()メソッドの使い分け (2025/08/08)
        // 特徴: forEach()メソッドは、拡張for文に比べてできないことがけっこうある。
        //
        // A. forEach()メソッドでできるところはforEach(), できなければ拡張for文
        // B. できるに越したことはなくて統一した方が世話ないから拡張for文
        //
        // o 現場のwebサービスのプログラムの中では、素直なループが圧倒的に多い
        // o できるに越したことはない、ってことはないという考え方、できないって安全
        // o 関数型プログラミングの影響
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList = prepareStageList();
        List<String> resultList = new ArrayList<>();
        for (String stage : stageList) {
            if (stage.contains("a")) {
                resultList.add(stage);
            }
        }
        for (String result : resultList) {
            log(result);
        }
        /* answer:
         * broadway
         * hangar
         * magiclamp
         */
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    public void test_iffor_refactor_foreach_to_forEach() {
        // write your code here
        List<String> stageList = prepareStageList();
        String sea = null;
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> {
            if (sb.length() > 0) { // sbが空でない場合つまり、該当の単語が見つかり、スタックに保存されている場合は何もしない
                return;
            } else {
                if (!stage.startsWith("br")) {
                    sb.append(stage);
                }
                if (stage.contains("ga")) {
                    return;
                    // これが実行されると、該当する最初の単語がスタックに保存され、のちの処理でスタックは更新されないようになる
                } else {
                    sb.replace(0, sb.length(), ""); // ここでsbを空にしておくことで、スタックされないようにする
                }
            }
        });
        sea = sb.toString();
        log(sea); // your answer? => hangar
        // if文が多く、複雑度が高くなってしまった。
        // TODO done jflute サイクロマティック複雑度が高くなってしまったので、もう少しシンプルに書き直したほうがよい？ by kumoshita
        // TODO kumoshita 1on1でライブコーディングした内容を再現してみましょう by jflute (2025/08/08)
    }

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * 以下のコードを実行したとき、変数 sea の中身は何になるでしょうか？
     * また、以下のコードをfor-each文に書き換えるとどうなるでしょうか？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_iffor_yourExercise() {
        List<String> stageList = prepareStageList();
        String sea = "";
        for (int i = 0; i < stageList.size(); i++) {
            if (i % 2 == 0) {
                sea = stageList.get(i);
            }
            if (sea.startsWith("ma")) {
                break;
            }
        }
        log(sea); // your answer? => 
    }

    // by jflute (2025/08/19)
    //StringBuilder seaSb = new StringBuilder();
    //AtomicInteger index = new AtomicInteger(); // 強引
    //stageList.forEach(stage -> {
    //    if (seaSb.indexOf("ma") == 0) {
    //        return;
    //    }
    //    if (index.get() % 2 == 0) {
    //        seaSb.setLength(0);
    //        seaSb.append(stage);
    //        //sea = stageList.get(i);
    //    }
    //    //if (sea.startsWith("ma")) {
    //    //    break;
    //    //}
    //    index.incrementAndGet();
    //});
    //log(seaSb);

    // #1on1: リファクタリングは思考のツール
    // https://jflute.hatenadiary.jp/entry/20121202/1354442627
    // #1on1: 変数名やメソッド名を保留する理由
    // 名前は相対的なものなので、全体像がハッキリしてから付けた方が効率が良い。
    // #1on1: プログラミングを書いている最中を見ること少ない話
    // #1on1: jfluteがライブコーディング積極的にやってる理由

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
        /* prepareStageList()のリストは以下のようになる。
         * 0. broadway
         * 1. dockside
         * 2. hangar
         * 3. magiclamp
         */
    }
}
