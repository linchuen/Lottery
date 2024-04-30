package com.cooba.component.playRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
@Component
public class GuessPositionNRule implements PlayRule {
    private final BigDecimal odds =  BigDecimal.valueOf(45).divide(BigDecimal.valueOf(1),2, RoundingMode.DOWN);// 45 / 1

    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        assert guessNumbers.size() == 1;
        assert playParameter.getPosition() != null;

        int position = playParameter.getPosition();
        int number = winningNumbers.get(position - 1);
        int guessNumber = guessNumbers.get(0);
        return number == guessNumber ? new WinResult(odds) : LoseResult.getInstance();
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.GuessPositionN;
    }
}
