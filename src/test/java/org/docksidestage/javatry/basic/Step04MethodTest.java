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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of method. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Kumoshita-a
 */
public class Step04MethodTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Method Call
    //                                                                         ===========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_method_call_basic() {
        String sea = supplySomething();
        log(sea); // your answer? => over
        // seaはsupplySomething()メソッドの戻り値である"over"が代入される。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_call_many() {
        String sea = functionSomething("mystic");
        consumeSomething(supplySomething());
        runnableSomething();
        log(sea); // your answer? => mysmys
        // seaはfunctionSomething("mystic")の戻り値であるちticがmisに変換された"mysmys"が代入される。
        // consumeSomething, runnableSomethingは呼び出されるが、seaの値には影響しない。
    }

    private String functionSomething(String name) {
        String replaced = name.replace("tic", "mys");
        log("in function: {}", replaced);
        return replaced;
        // ticをmysに置き換えた文字列を返すメソッド
    }

    private String supplySomething() {
        String sea = "over";
        log("in supply: {}", sea);
        return sea;
        // overという文字列を返すメソッド
    }

    private void consumeSomething(String sea) {
        log("in consume: {}", sea.replace("over", "mystic"));
        // logを出すだけ
    }

    private void runnableSomething() {
        String sea = "outofshadow";
        log("in runnable: {}", sea);
        // logを出すだけ
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_object() {
        St4MutableStage mutable = new St4MutableStage();
        int sea = 904;
        boolean land = false;
        helloMutable(sea - 4, land, mutable);
        if (!land) {
            sea = sea + mutable.getStageName().length();
        }
        log(sea); // your answer? => 910
        // mutableはSt4MutableStageのインスタンスである。
        // helloMutableメソッドを呼び出すと、seaはsea - 4の値である900が渡され、landはfalseが渡される。
        // helloMutableメソッド内でseaはインクリメントされて901になり、landはtrueとなっているが、参照渡しのため、元のlandには影響しない。
        // piariにはもとのmutableが渡されているため、helloMutableメソッド内での変更が反映される。
        // したがって、setStageName("mystic")が呼び出され、mutableのstageNameは"mystic"に変更される。
        // その後、if文の条件はlandがfalseなのでtrueとなり、seaはsea + mutable.getStageName().length()が実行される。
        // つまり、seaは904 + "mystic".length() = 904 + 6 = 910となる。
        // したがって、最終的にseaには910が代入される
    }

    private int helloMutable(int sea, Boolean land, St4MutableStage piari) {
        sea++;
        land = true;
        piari.setStageName("mystic");
        return sea;
    }

    private static class St4MutableStage {

        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private int inParkCount;
    private boolean hasAnnualPassport;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_instanceVariable() {
        hasAnnualPassport = true;
        int sea = inParkCount;
        offAnnualPassport(hasAnnualPassport);
        for (int i = 0; i < 100; i++) {
            goToPark();
        }
        ++sea;
        sea = inParkCount;
        log(sea); // your answer? => 100
        // inParkCountは初期値0で、offAnnualPassportではhasAnnualPassportに影響はないので、hasAnnualPassportはtrueのままである。
        // よって、goToPark()メソッドがfor文で100回呼び出されるので、inParkCountは100回インクリメントされる。
        // その後、++seaでseaは1増えて1となるが、最後にsea = inParkCount;でinParkCountの値が再代入されるため、最終的にseaには100が代入される。
    }

    private void offAnnualPassport(boolean hasAnnualPassport) {
        hasAnnualPassport = false;
    }
    // ここのhasAnnualPassportはprivateインスタンス変数ではなく、メソッドの引数なので、インスタンス変数には影響しない。（名前が一緒だからややこしいやつ）

    private void goToPark() {
        if (hasAnnualPassport) {
            ++inParkCount;
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    // write instance variables here
    /**
     * Make private methods as followings, and comment out caller program in test method:
     * <pre>
     * o replaceAwithB(): has one argument as String, returns argument replaced "A" with "B" as String 
     * o replaceCwithB(): has one argument as String, returns argument replaced "C" with "B" as String 
     * o quote(): has two arguments as String, returns first argument quoted by second argument (quotation) 
     * o isAvailableLogging(): no argument, returns private instance variable "availableLogging" initialized as true (also make it separately)  
     * o showSea(): has one argument as String argument, no return, show argument by log()
     * </pre>
     * (privateメソッドを以下のように定義して、テストメソッド内の呼び出しプログラムをコメントアウトしましょう):
     * <pre>
     * o replaceAwithB(): 一つのString引数、引数の "A" を "B" に置き換えたStringを戻す 
     * o replaceCwithB(): 一つのString引数、引数の "C" を "B" に置き換えたStringを戻す 
     * o quote(): 二つのString引数、第一引数を第二引数(引用符)で囲ったものを戻す 
     * o isAvailableLogging(): 引数なし、privateのインスタンス変数 "availableLogging" (初期値:true) を戻す (それも別途作る)  
     * o showSea(): 一つのString引数、戻り値なし、引数をlog()で表示する
     * </pre>
     */
    public void test_method_making() {
        // use after making these methods
        String replaced = replaceCwithB(replaceAwithB("ABC"));
        String sea = quote(replaced, "'");
        if (isAvailableLogging()) {
            showSea(sea);
        }
    }

    // write methods here

    private final boolean availableLogging = true;

    private String replaceAwithB(String str) {
        return str.replace("A", "B");
    }

    private String replaceCwithB(String str) {
        return str.replace("C", "B");
    }

    private String quote(String str, String quotation) {
        return (quotation + str + quotation);
    }

    private boolean isAvailableLogging() {
        return availableLogging;
    }

    private void showSea(String sea) {
        log(sea);
    }
}
