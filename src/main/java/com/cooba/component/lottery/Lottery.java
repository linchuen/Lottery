package com.cooba.component.lottery;

import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LorreryRoundEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.LotteryNumberEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;

import java.util.List;

public interface Lottery {

    PlayResult checkNumbers(GameRuleEnum ruleEnum, List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter);

    void generateNextRoundNumbers(LorreryRoundEnum roundEnum, LotteryNumberEnum numberEnum);

    LotteryEnum getLotteryEnum();
}
