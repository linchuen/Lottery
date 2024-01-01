package com.cooba.enums;

import lombok.Getter;

@Getter
public enum AssetEnum {
    TWD(1),
    USD(2),
    BTC(3);

    AssetEnum(int id) {
        this.id = id;
    }

    private final int id;
}
