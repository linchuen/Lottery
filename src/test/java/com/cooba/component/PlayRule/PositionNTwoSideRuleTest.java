package com.cooba.component.PlayRule;

import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.TieResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PositionNTwoSideRuleTest {
    @InjectMocks
    PositionNTwoSideRule positionNTwoSideRule;

    @Test
    void checkBig() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().position(7).isBig(true).build();

        PlayResult result = positionNTwoSideRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertFalse(result.isWin());
    }

    @Test
    void checkSmall() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().position(7).isBig(false).build();

        PlayResult result = positionNTwoSideRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void checkOdd() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().position(7).isOdd(true).build();

        PlayResult result = positionNTwoSideRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void checkEven() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().position(7).isOdd(false).build();

        PlayResult result = positionNTwoSideRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertFalse(result.isWin());
    }

    @Test
    void checkTie() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 49);
        PlayParameter playParameter = PlayParameter.builder().position(7).isBig(false).build();

        PlayResult result = positionNTwoSideRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(((TieResult)result).isTie());
    }
}