package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author Kumoshita-a
 */

/**
 * 利用可能な時間帯種別を表すEnum。今後、朝のみ(MORNING_ONLY)など増やす場合はここに追加する。
 */
public enum TimeSlot {
    /** 終日利用可能 */
    ALL_DAY,
    /** 夜のみ利用可能 (例えば17:00以降) */
    NIGHT_ONLY;
}
