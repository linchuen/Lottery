package com.cooba.component.Lottery;

import com.cooba.component.PlayRule.PlayRule;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 玩法
 * 中n
 * 猜第n球
 * 第n球 大
 * 第n球 小
 * 第n球 單
 * 第n球 雙
 * 總和 大
 * 總和 小
 * 總和 單
 * 總和 雙
 * 自選n不中
 * 色波
 */
public class MarkSixLottery implements Lottery {
    private final List<PlayRule> playRules;
    private final Map<GameRuleEnum, PlayRule> playRuleMap;

    public MarkSixLottery(List<PlayRule> playRules) {
        this.playRules = playRules;
        this.playRuleMap = playRules.stream()
                .collect(Collectors.toMap(PlayRule::getGameRuleEnum, Function.identity()));
    }

    @Override
    public PlayResult checkNumbers(GameRuleEnum ruleEnum, List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        PlayRule playRule = playRuleMap.get(ruleEnum);
        return playRule.decideResult(winningNumbers, guessNumbers, playParameter);
    }

    @Override
    public LotteryEnum getLotteryEnum() {
        return LotteryEnum.MarkSix;
    }
}
