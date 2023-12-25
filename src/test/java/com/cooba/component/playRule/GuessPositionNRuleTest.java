package com.cooba.component.playRule;

import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GuessPositionNRuleTest {
    @InjectMocks
    GuessPositionNRule guessPositionNRule;

    @Test
    void isWin() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().position(7).build();

        PlayResult result = guessPositionNRule.decideResult(winningNumbers, List.of(7), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void isLose() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().position(7).build();

        PlayResult result = guessPositionNRule.decideResult(winningNumbers, List.of(8), playParameter);
        Assertions.assertFalse(result.isWin());
    }
}