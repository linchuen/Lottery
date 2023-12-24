package com.cooba.enums;

import lombok.Getter;

@Getter
public enum GameStatusEnum {
    WIN(1),
    LOSE(0),
    TIE(2);

    private final int code;

    GameStatusEnum(int code) {
        this.code = code;
    }
}
