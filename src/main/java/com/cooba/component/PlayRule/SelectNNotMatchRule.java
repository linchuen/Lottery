package com.cooba.component.PlayRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;

import java.math.BigDecimal;
import java.util.List;

public class SelectNNotMatchRule implements PlayRule {
    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        int selectSize = guessNumbers.size();
        long notMatchCount = guessNumbers.stream().filter(number -> !winningNumbers.contains(number)).count();

        switch (selectSize) {
            case 2 -> {
                return notMatchCount == selectSize ? new WinResult(BigDecimal.valueOf(2)) : LoseResult.getInstance();
            }
            case 3 -> {
                return notMatchCount == selectSize ? new WinResult(BigDecimal.valueOf(3)) : LoseResult.getInstance();
            }
            case 4 -> {
                return notMatchCount == selectSize ? new WinResult(BigDecimal.valueOf(4)) : LoseResult.getInstance();
            }
            case 5 -> {
                return notMatchCount == selectSize ? new WinResult(BigDecimal.valueOf(5)) : LoseResult.getInstance();
            }
            case 6 -> {
                return notMatchCount == selectSize ? new WinResult(BigDecimal.valueOf(6)) : LoseResult.getInstance();
            }
            case 7 -> {
                return notMatchCount == selectSize ? new WinResult(BigDecimal.valueOf(7)) : LoseResult.getInstance();
            }
            default -> {
                return LoseResult.getInstance();
            }
        }
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.SelectNNotMatch;
    }
}
