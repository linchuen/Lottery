package com.cooba.component.playRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class SelectNRule implements PlayRule {
    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        int selectSize = guessNumbers.size();
        long matchCount = guessNumbers.stream().filter(winningNumbers::contains).count();

        switch (selectSize) {
            case 2 -> {
                return matchCount == selectSize ? new WinResult(BigDecimal.valueOf(2)) : LoseResult.getInstance();
            }
            case 3 -> {
                return matchCount == selectSize ? new WinResult(BigDecimal.valueOf(3)) : LoseResult.getInstance();
            }
            case 4 -> {
                return matchCount == selectSize ? new WinResult(BigDecimal.valueOf(4)) : LoseResult.getInstance();
            }
            case 5 -> {
                return matchCount == selectSize ? new WinResult(BigDecimal.valueOf(5)) : LoseResult.getInstance();
            }
            case 6 -> {
                return matchCount == selectSize ? new WinResult(BigDecimal.valueOf(6)) : LoseResult.getInstance();
            }
            case 7 -> {
                return matchCount == selectSize ? new WinResult(BigDecimal.valueOf(7)) : LoseResult.getInstance();
            }
            default -> {
                return LoseResult.getInstance();
            }
        }
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.SelectN;
    }
}
