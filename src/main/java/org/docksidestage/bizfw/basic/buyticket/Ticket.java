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
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int displayPrice; // written on ticket, park guest can watch this
    private boolean alreadyIn; // true means this ticket is unavailable

    // TODO kumoshita [いいね] コメントでe.g.を使うことで具体例を示しつつ、列挙断定をしてないので安定的 by jflute (2025/09/19)
    // #1on1: byTwoDayPassport()の例: * @return 結果オブジェクト e.g. チケットとお釣り
    // (e.g.じゃないときは、"など" を付けるもあり)
    private int remainingDays = 1; // how many days remaining, e.g. 1 or 2

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Ticket(int displayPrice) {
        this.displayPrice = displayPrice;
    }

    public Ticket(int displayPrice, int remainingDays) {
        this.displayPrice = displayPrice;
        this.remainingDays = remainingDays;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    // #1on1: alreadyInの扱いについて、まだちょっと悩み中 (2025/09/19)
    public void doInPark() {
        if (alreadyIn && remainingDays <= 0) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        --remainingDays;
        alreadyIn = true;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean isAlreadyIn() {
        return alreadyIn;
    }

    public boolean isTwoDayPassport() {
        return remainingDays == 2;
    }
}
