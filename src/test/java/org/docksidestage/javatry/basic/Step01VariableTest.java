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

import java.math.BigDecimal;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Kumoshita-a
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => mystic8null:mai
        // nullは'"null"として文字列に変換される
        // done kumoshita [いいね] 昔のインターネット画面ではよく「こんにちはnullさん」とか表示ありました^^ by jflute (2025/07/22)
        // メール文面だと最近でも時々見かけますね。
        // とはいえ、nullって出ることで間違いがわかりやすいって面もあるので、そうなってるのかなと。
        // 一方で、例えば C# だと空文字に変換されます。(エラーになる言語はあまり聞いたことないかも!?)
        // 些細な違いですが、言語によってこういうところ変わってきたりします。

        // #1on1: メールは気合が入らないながら、リカバリが難しくて慎重に作らないいけないものだったり
        // #1on1: 言語設計デザインにも寄る話
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman
        // seaが持つのはlandsの参照であり、値ではないので、landの値が変わっても影響がない
        // done kumoshita [いいね] しっかり変数は参照を持つだけっての理解されててGoodです by jflute (2025/07/22)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415
        // seaが持つのはlandsの参照であり、値ではないので、landの値が変わっても影響がない
        // done kumoshita [ふぉろー] プリミティブ型の場合は、厳密には値そのものを持っているようなイメージです by jflute (2025/07/22)
        // とはいえ、sea は415を持っていて、landが416に差し替えられてるだけなので影響がないことには変わりはないです。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 416
        // BigDecimalは不変なので、sea.addしても値は増えない→何かしらに代入する必要あり
        // done kumoshita [いいね] 不変 (immutable) の概念を理解してらっしゃるの素晴らしいです by jflute (2025/07/22)
        // 一方で、immutableのクラスって具体的に何が良いのでしょう？というのを1on1で一緒に議論できればと。

        // done jflute 1on1にてimmutableについて深堀り予定 (2025/07/22)
        // (↑のtodoはくぼ用のtodoということで、そのもの残しておいてください)
        // #1on1: BigDecimalで、カーソル当てるとJavaDoc出ます話 (レアなクラスはJavaDoc見るなり大切)
        // #1on1: IntelliJで、メソッド補完時にcontrol+Jを押すとJavaDoc出ます話
        // #1on1: add()のソースコードリーディング、構造だけ読んで、フォーカス当てて読む

        // #1on1: immutable のメリットは？
        //  → Ans. 安全だな〜、予期せぬところで予期せぬことが起きない、断定できる、管理できる
        // A. 安全性: (けっこう多い)read-only想定の場面で、変な間違いがおきない
        // B. 可読性: (けっこう多い)read-only想定の場面で、読む側が読まない良いところが増える
        // C. 管理が世話ない: 状態の変化を追うってのが、人間にとってはつらい
        //  → 人間都合
        // immutable のデメリットは？
        //  → Ans. 変更加えたいときちょと面倒なときがある
        // A. メモリよく使う (現代では、メモリめちゃあるのであまり気にしない)
        // B. sea.add(new BigDecimal(1)); 戻り値取らない問題 (mutableと混ざることの問題)
        // C. immutableにするのに手間がかかるケースがある (言語の文法次第)
        // o 言語でのimmutable/mutableのバランス
        // o 個人的にJavaでは、8:2くらいな感覚
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => null
        // インスタンス変数は初期化されていないので、Stringのときはnullが入る
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0
        // インスタンス変数は初期化されていないので、intのときは0が入る
        // 0が入る理由：intはプリミティブ型であり、nullが許容されておらず、初期値として0が設定されている
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => null
        // インスタンス変数は初期化されていないので、Integerのときはnullが入る
        // nullが入る理由：Integerはオブジェクト型であり、nullが許容されるため、初期値はnull
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bigband|1|null|magician
        // インスタンス変数を直接編集したものは値が変更される
        // instanceBroadway = "bbb";　(最初はbbbが入っている)
        // instanceMagiclamp = "magician"; (最初はmagicianが入っている)
        // instanceBroadway = "bigband"; (ここでbigbandに変更される)
        // ++instanceDockside; (ここで初期値0に1が加算されて1になる)
        // 引数は参照渡しなので、メソッド内での変更は呼び出し元には影響しない
        // instanceMagiclamp = "burn";
        // done kumoshita [いいね] メソッド引数の参照渡しの概念しっかり理解されててGoodです by jflute (2025/07/22)
        // test_側とhelp側でたまたま同じ名前の変数で引き渡ししていますが、渡ってるのは変数ではなく中身の参照(アドレス)ですからね。
        // 引数としてのローカル変数のinstanceMagiclampに新しい参照を代入しても、
        // インスタンス変数のinstanceMagiclampの指し示す先には何も影響がないと。
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor
        // seaはString型であり、immutableなので、メソッド内での変更は呼び出し元に影響しない
        // concatは引数を文字を追加した新しい文字列を返すが、seaはImmutableなので、元の文字列は変更されない
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => harbor416
        // seaはStringBuilder型であり、mutableなので、メソッド内での変更は呼び出し元に影響する
        // appendは引数を文字列に追加して、元のStringBuilderを変更する
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor
        // seaはStringBuilder型であり、mutableなので、メソッド内での変更は呼び出し元に影響するが、ここでは新しいStringBuilderを作成してseaに代入したので呼び出し元のseaは変更されない
        // seaを直接編集しておらず、新しいStringBuilderを作成して代入しているため、呼び出し元のseaは影響を受けない
        // done kumoshita [いいね] newされていることをしっかり認識されているのGood by jflute (2025/07/22)
        // 同じクラスであっても別インスタンスであれば別物ですからね。
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */

    private int piari;

    public void test_variable_writing() {
        // define variables here
        String sea = "mystic";
        Integer land = null;

        log(sea + "," + land + "," + piari);
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========

    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * logの結果は何ですか？（参照渡しと値渡しの違いにフォーカスしました）
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */

    private static class DataContainer {
        public String sea;

        public DataContainer(String sea) {
            this.sea = sea;
        }

        @Override
        public String toString() {
            return this.sea;
        }
    }

    public void test_variable_yourExercise() {
        // write your code here
        DataContainer dataA = new DataContainer("initialDataA");
        DataContainer dataB = new DataContainer("initialDataB");

        changeDataContainer(dataA, dataB);

        log(dataA.toString() + "|" + dataB.toString()); // answer? => 
    }

    private static void changeDataContainer(DataContainer dataA, DataContainer dataB) {
        dataA.sea = "changed";
        dataB = new DataContainer("newData");
    }

    // done kumoshita [いいね] 実際にコード読むだけでやってみました。ちょっとドキドキしますね(^^ by jflute (2025/07/22)
    // mutable地獄ってやつですね。ふー合ってた良かった笑。

    // #1on1: 一行一行どころか1文字1文字、じっくり見て検証するという力も身につけてもらうためのクイズ
}
