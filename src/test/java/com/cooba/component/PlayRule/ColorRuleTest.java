package com.cooba.component.PlayRule;

import com.cooba.enums.ColorEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ColorRuleTest {
    @InjectMocks
    ColorRule colorRule;

    @Test
    void isRed() {
        //紅波: 1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        PlayParameter playParameter = PlayParameter.builder().color(ColorEnum.RED).build();

        PlayResult result = colorRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void isBlue() {
        //藍波: 3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 9);
        PlayParameter playParameter = PlayParameter.builder().color(ColorEnum.BLUE).build();

        PlayResult result = colorRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void isGreen() {
        //綠波: 5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 11);
        PlayParameter playParameter = PlayParameter.builder().color(ColorEnum.GREEN).build();

        PlayResult result = colorRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void isLose() {
        //綠波: 5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 11);
        PlayParameter playParameter = PlayParameter.builder().color(ColorEnum.RED).build();

        PlayResult result = colorRule.decideResult(winningNumbers, Collections.emptyList(), playParameter);
        Assertions.assertFalse(result.isWin());
    }
}