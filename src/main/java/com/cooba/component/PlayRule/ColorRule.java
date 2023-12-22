package com.cooba.component.PlayRule;

import com.cooba.enums.ColorEnum;
import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;

import java.util.List;
import java.util.stream.Stream;

public class ColorRule implements PlayRule {
    private final List<Integer> redBalls = Stream.of(1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46).toList();
    private final List<Integer> blueBalls = Stream.of(3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48).toList();
    private final List<Integer> greenBalls = Stream.of(5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49).toList();

    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        int specialNumber = winningNumbers.get(6);

        ColorEnum colorEnum = playParameter.getColor();
        switch (colorEnum) {
            case RED -> {
                return redBalls.contains(specialNumber) ? new WinResult() : LoseResult.getInstance();
            }
            case BLUE -> {
                return blueBalls.contains(specialNumber) ? new WinResult() : LoseResult.getInstance();
            }
            case GREEN -> {
                return greenBalls.contains(specialNumber) ? new WinResult() : LoseResult.getInstance();
            }
            default -> {
                return LoseResult.getInstance();
            }
        }
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.Color;
    }
}
