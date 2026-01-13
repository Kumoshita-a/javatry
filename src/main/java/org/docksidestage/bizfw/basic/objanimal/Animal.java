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
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingCallback;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;

/**
 * The object for animal(動物).
 * @author jflute
 * @author Kumoshita-a
 */
public abstract class Animal implements Loudable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // (Logger removed: bark logging moved to BarkingProcess)

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected int hitPoint; // is HP
    private final BarkingProcess barkingProcess = new BarkingProcess();

    // #1on1: ファクトリーメソッドでオーバーライドするパターン (2026/01/13)
    //protected BarkingProcess createBarkingProcess() {
    //    return new BarkingProcess();
    //}
    
    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Animal() {
        hitPoint = getInitialHitPoint();
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    // #1on1: どこからどこまでがオブジェクト指向？コールバックの実現方法は？ (2026/01/13)
    // 王道のオブジェクト指向と、ミクロなオブジェクト指向などなど。
    public BarkedSound bark() {
        return barkingProcess.executeBark(new BarkingCallback() {
            @Override
            public String getBarkWord() {
                return Animal.this.getBarkWord();
            }
            @Override
            public void downHitPoint() {
                Animal.this.downHitPoint();
            }
            @Override
            public void afterBreatheIn() {
                Animal.this.hookAfterBreatheIn();
            }
        });
    }

    protected abstract String getBarkWord();
    // 利用例:具体的なアニマルクラスで定義されている→Dog: "wan"

    // #1on1: ファクトリーメソッドでProcessをサブクラスに公開する方式との違い (2026/01/13)
    // 柔軟性と堅牢さ、どっちを取るか？
    protected void hookAfterBreatheIn() {
        // デフォルトではなにも定義しない(Zombieのためのフックメソッド)
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    // done kumo [いいね] publicに関するコメントがあるの素晴らしい by jflute (2025/10/28)
    // done kumo 修行++: やはりpublicにしたくないので、なんとかprotectedに戻せるようにしましょう by jflute (2025/10/28)
    // -> BarkingCallbackインターフェースを使って、protectedメソッドにした
    // 関数型インターフェースを使う方法もあるらしい？ by Kumoshita-a (2026/1/13)
    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    @Override
    public String soundLoudly() {
        return bark().getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }
}
