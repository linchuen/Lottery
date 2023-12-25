package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum WalletEnum {
    SIMPLE(1);

    private final int id;
    private static final Map<Integer, WalletEnum> WalletEnumMap = new HashMap<>();

    WalletEnum(int id) {
        this.id = id;
    }

    static {
        for (WalletEnum value : WalletEnum.values()) {
            WalletEnumMap.put(value.id, value);
        }
    }

    public static WalletEnum getWalletById(int id) {
        return WalletEnumMap.get(id);
    }
}
