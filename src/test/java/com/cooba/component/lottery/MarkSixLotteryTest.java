package com.cooba.component.lottery;

import com.cooba.component.playRule.ColorRule;
import com.cooba.component.playRule.GuessPositionNRule;
import com.cooba.component.playRule.PositionNTwoSideRule;
import com.cooba.component.playRule.SelectNNotMatchRule;
import com.cooba.component.playRule.SelectNRule;
import com.cooba.component.playRule.SumRule;
import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class MarkSixLotteryTest {
    private static MarkSixLottery markSixLottery;

    @BeforeAll
    public static void init() {
        markSixLottery = new MarkSixLottery(List.of(
                new ColorRule(),
                new GuessPositionNRule(),
                new PositionNTwoSideRule(),
                new SelectNRule(),
                new SelectNNotMatchRule(),
                new SumRule()
        ));
    }

    @Test
    public void test() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> guessNumbers = List.of(7);
        PlayParameter playParameter = PlayParameter.builder().position(7).build();

        PlayResult result = markSixLottery.checkNumbers(GameRuleEnum.GuessPositionN, winningNumbers, guessNumbers, playParameter);
        Assertions.assertTrue(result.isWin());
    }
}