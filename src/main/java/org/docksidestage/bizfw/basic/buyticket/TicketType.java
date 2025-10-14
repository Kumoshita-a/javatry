package org.docksidestage.bizfw.basic.buyticket;

// TODO done kumo javadocが分離してる!? by jflute (2025/09/30)
/**
 * @author Kumoshita-a
 * チケット種別。価格、利用可能日数、デフォルト在庫数、利用可能時間帯を保持する。
 * 新しいチケットを追加したい場合はEnum定数を追加するだけで良い。
 */
public enum TicketType {
    // #1on1: IntelliJでもEclipseでも、フォーマッターで横に一列並んでしまうのでhackで//技 (2025/09/30)
    // #1on1: よもやま: 現場(DBFlute)だと区分値のenumはほぼほぼ自動生成しちゃう (2025/09/30)
    ONE_DAY(7400, 1, 10, TimeSlot.ALL_DAY), //
    TWO_DAY(13200, 2, 20, TimeSlot.ALL_DAY), // 
    FOUR_DAY(22400, 4, 40, TimeSlot.ALL_DAY), //
    NIGHT_ONLY_TWO_DAY(7400, 2, 15, TimeSlot.NIGHT_ONLY),
    ;

    private final int price;
    private final int days;
    private final int defaultInventory;
    private final TimeSlot timeSlot;

    TicketType(int price, int days, int defaultInventory, TimeSlot timeSlot) {
        this.price = price;
        this.days = days;
        this.defaultInventory = defaultInventory;
        this.timeSlot = timeSlot;
    }

    public int getPrice() {
        return price;
    }

    public int getDays() {
        return days;
    }

    public int getDefaultInventory() {
        return defaultInventory;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }
}
