package com.cooba.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    create(0),
    pay(1),
    cancel(2),
    settle(3),
    settleFailed(4),
    award(5),
    error(6);

    private final int code;

    OrderStatusEnum(int code) {
        this.code = code;
    }
}
