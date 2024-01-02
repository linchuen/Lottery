package com.cooba.enums;

import lombok.Getter;

@Getter
public enum QueueEnum {
    SETTLE_COMPLETED("SETTLE_COMPLETED");

    private final String name;

    QueueEnum(String name) {
        this.name = name;
    }


}
