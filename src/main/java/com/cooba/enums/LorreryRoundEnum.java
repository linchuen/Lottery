package com.cooba.enums;

import lombok.Getter;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Getter
public enum LorreryRoundEnum {
    MarkSix(LocalTime.of(0, 0, 0), LocalTime.of(23, 30, 0), 10, TimeUnit.MINUTES, 1,"%03d");

    private final LocalTime start;
    private final LocalTime end;
    private final int intervalTime;
    private final TimeUnit timeUnit;
    private final int startRound;
    private final String format;

    LorreryRoundEnum(LocalTime start, LocalTime end, int intervalTime, TimeUnit timeUnit, int startRound, String format) {
        this.start = start;
        this.end = end;
        this.intervalTime = intervalTime;
        this.timeUnit = timeUnit;
        this.startRound = startRound;
        this.format = format;
    }
}
