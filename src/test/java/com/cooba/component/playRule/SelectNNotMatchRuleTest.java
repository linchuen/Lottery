package com.cooba.component.playRule;

import com.cooba.object.PlayResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SelectNNotMatchRuleTest {
    @InjectMocks
    SelectNNotMatchRule selectNNotMatchRule;

    @Test
    void isWin() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);

        PlayResult result = selectNNotMatchRule.decideResult(winningNumbers, List.of(1,2), null);
        Assertions.assertFalse(result.isWin());
    }

    @Test
    void isLose() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);

        PlayResult result = selectNNotMatchRule.decideResult(winningNumbers, List.of(8,3), null);
        Assertions.assertFalse(result.isWin());
    }
}