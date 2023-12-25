package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum LotteryEnum {
    MarkSix(1);

    private final int id;
    private static final Map<Integer, LotteryEnum> lotteryEnumMap = new HashMap<>();

    LotteryEnum(int id) {
        this.id = id;
    }

    static {
        for (LotteryEnum value : LotteryEnum.values()) {
            lotteryEnumMap.put(value.id, value);
        }
    }

    public static Optional<LotteryEnum> getLotteryById(int id) {
        return Optional.ofNullable(lotteryEnumMap.get(id));
    }
}
