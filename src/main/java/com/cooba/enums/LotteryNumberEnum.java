package com.cooba.enums;

import lombok.Getter;

@Getter
public enum LotteryNumberEnum {
    MarkSix(1, 45, 7, false);

    private final int min;
    private final int max;
    private final int length;
    private final boolean isRepeat;

    LotteryNumberEnum(int min, int max, int length, boolean isRepeat) {
        this.min = min;
        this.max = max;
        this.length = length;
        this.isRepeat = isRepeat;
    }
}
