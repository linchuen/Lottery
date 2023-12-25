package com.cooba.component.playRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;

import java.util.List;

public interface PlayRule {
    PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter);

    GameRuleEnum getGameRuleEnum();
}
