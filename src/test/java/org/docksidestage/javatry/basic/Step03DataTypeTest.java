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
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of data type. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author Kumoshita-a
 */
public class Step03DataTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          Basic Type
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_datatype_basicType() {
        String sea = "mystic";
        Integer land = 416;
        LocalDate piari = LocalDate.of(2001, 9, 4);
        LocalDateTime bonvo = LocalDateTime.of(2001, 9, 4, 12, 34, 56);
        Boolean dstore = true;
        BigDecimal amba = new BigDecimal("9.4");

        piari = piari.plusDays(1);
        land = piari.getYear();
        bonvo = bonvo.plusMonths(1);
        land = bonvo.getMonthValue();
        land--;
        if (dstore) {
            BigDecimal addedDecimal = amba.add(new BigDecimal(land));
            sea = String.valueOf(addedDecimal);
        }
        log(sea); // your answer? => 18.4
        // landの値の遷移：416 → 2001 → 10（初期値は9だが、bonvo = bonvo.plusMonths(1)で10に変わる） → 9
        // dstoreはtrueなのでif内の処理が実行され、ambaにlandを足してseaに代入される。
        // ambaの値は9.4なので、9.4 + 9 = 18.4となり、最終的にseaには"18.4"が代入される。 
    }

    // ===================================================================================
    //                                                                           Primitive
    //                                                                           =========
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_primitive() {
        byte sea = 127; // max
        short land = 32767; // max
        int piari = 1;
        long bonvo = 9223372036854775807L; // max
        float dstore = 1.1f;
        double amba = 2.3d;
        char miraco = 'a';
        boolean dohotel = miraco == 'a';
        if (dohotel && dstore >= piari) {
            bonvo = sea;
            land = (short) bonvo;
            bonvo = piari;
            sea = (byte) land;
            if (amba == 2.3D) {
                sea = (byte) amba;
            }
        }
        if ((int) dstore > piari) {
            sea = 0;
        }
        log(sea); // your answer? => 2
        // seaだけに注目すると、seaへの代入箇所は三箇所ある。
        // まず、if文の中で、sea = (byte) land;で127が代入される。
        // 次に、もう一つのif文の中で、sea = (byte) amba;で2.3をbyte型にキャストする
        // 残り一つは、if文の条件がfalseだった場合に、sea = 0;で0が代入される。
        // dstoreの値は1.1fなので、(int) dstoreは1となり、piariは1なので、if文の条件はfalseとなる。
        // したがって、if文の条件式はfalseとなり、処理は実行されない。
        // よって、2.3をbyte型にキャストした結果である2が最終的にseaに代入されている。
    }

    // ===================================================================================
    //                                                                              Object
    //                                                                              ======
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_object() {
        St3ImmutableStage stage = new St3ImmutableStage("hangar");
        String sea = stage.getStageName();
        log(sea); // your answer? => hangar
        // stageはSt3ImmutableStageのインスタンスで、コンストラクタで初期化時にhangarを引数に渡しているので、インスタンス変数のstageNameは"hangar"が入る。
        // getStageName()メソッドはstageNameを返すメソッドなので、stage.getStageName()を呼び出すとstageNameに入っている"hangar"が返される。
        // そのため、seaには"hangar"が代入される。
    }

    private static class St3ImmutableStage {

        private final String stageName;

        public St3ImmutableStage(String stageName) {
            this.stageName = stageName;
        }

        public String getStageName() {
            return stageName;
        }
    }
}
