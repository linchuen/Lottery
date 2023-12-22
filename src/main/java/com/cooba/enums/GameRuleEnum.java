package com.cooba.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum GameRuleEnum {
    Color(1),
    PositionNTwoSide(2),
    GuessPositionN(3),
    SelectNNotMatch(4),
    SelectN(5),
    Sum(6);

    private final int id;
    private static final Map<Integer, GameRuleEnum> ruleEnumMap = new HashMap<>();

    GameRuleEnum(int id) {
        this.id = id;
    }

    static {
        for (GameRuleEnum value : GameRuleEnum.values()) {
            ruleEnumMap.put(value.id, value);
        }

    }

    public static GameRuleEnum getRuleById(int id) {
        return ruleEnumMap.get(id);
    }
}
