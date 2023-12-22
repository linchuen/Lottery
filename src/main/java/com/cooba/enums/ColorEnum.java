package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ColorEnum {
    RED(1),
    BLUE(2),
    GREEN(3);

    private final int id;
    private static final Map<Integer, ColorEnum> colorEnumMap = new HashMap<>();

    ColorEnum(int id) {
        this.id = id;
    }

    static {
        for (ColorEnum value : ColorEnum.values()) {
            colorEnumMap.put(value.id, value);
        }

    }

    public static ColorEnum getColorById(int id) {
        return colorEnumMap.get(id);
    }
}
