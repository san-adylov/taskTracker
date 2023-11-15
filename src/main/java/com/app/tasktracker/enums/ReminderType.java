package com.app.tasktracker.enums;

public enum ReminderType {
    NONE(0),
    FIVE_MINUTE(5),
    TEN_MINUTE(10),
    FIFTEEN_MINUTE(15),
    THIRD_MINUTE(30);

    final int minute;

    ReminderType(int minute) {
        this.minute = minute;
    }

    public int getMinute() {
        return minute;
    }
}