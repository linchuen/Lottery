package com.cooba.component.playRule;

import com.cooba.component.playRule.common.TwoSideCommonMethod;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class SumRuleTest {
    SumRule sumRule = new SumRule(new TwoSideCommonMethod());

    @Test
    void checkBig() {
        List<Integer> winningNumbers = List.of(39, 40, 41, 42, 43, 44, 45, 46);
        PlayParameter playParameter = PlayParameter.builder().isBig(true).build();

        PlayResult result = sumRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void checkSmall() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().isBig(false).build();

        PlayResult result = sumRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void checkOdd() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().isOdd(false).build();

        PlayResult result = sumRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void checkEven() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 8);
        PlayParameter playParameter = PlayParameter.builder().isOdd(false).build();

        PlayResult result = sumRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertFalse(result.isWin());
    }
}