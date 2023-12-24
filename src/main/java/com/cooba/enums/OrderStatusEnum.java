package com.cooba.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    create(0),
    pay(1),
    cancel(2),
    settle(3),
    award(4),
    error(5);

    private final int code;

    OrderStatusEnum(int code) {
        this.code = code;
    }
}
