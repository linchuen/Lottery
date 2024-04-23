package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum WalletEnum {
    INNER(1);

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

    public static Optional<WalletEnum> getWalletById(int id) {
        return Optional.ofNullable(WalletEnumMap.get(id));
    }
}
