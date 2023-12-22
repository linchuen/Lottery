package com.cooba.component.Lottery;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;

import java.util.List;

public interface Lottery {

    PlayResult checkNumbers(GameRuleEnum ruleEnum, List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter);
}
