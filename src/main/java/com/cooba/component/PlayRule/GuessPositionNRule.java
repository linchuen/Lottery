package com.cooba.component.PlayRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;

import java.util.List;

public class GuessPositionNRule implements PlayRule {
    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        assert guessNumbers.size() == 1;
        assert playParameter.getPosition() != null;

        int position = playParameter.getPosition();
        int number = winningNumbers.get(position - 1);
        int guessNumber = guessNumbers.get(0);
        return number == guessNumber ? new WinResult() : LoseResult.getInstance();
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.GuessPositionN;
    }
}
