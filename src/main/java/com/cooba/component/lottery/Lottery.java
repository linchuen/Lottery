package com.cooba.component.lottery;

import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LorreryRoundEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.LotteryNumberEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinningNumberInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Lottery {

    PlayResult checkNumbers(GameRuleEnum ruleEnum, List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter);

    Optional<WinningNumberInfo> generateNextRoundNumbers(LocalDateTime time);

    long calculateNextRound(LocalDateTime now);

    LocalDateTime calculateNextRoundTime(LocalDateTime now);

    LotteryEnum getLotteryEnum();

    LotteryNumberEnum getLotteryNumberEnum();

    LorreryRoundEnum getLorreryRoundEnum();
}
