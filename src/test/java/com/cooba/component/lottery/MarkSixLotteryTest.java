package com.cooba.component.lottery;

import com.cooba.component.numberGenerator.NumberGenerator;
import com.cooba.config.PlayRuleScan;
import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinningNumberInfo;
import com.cooba.repository.FakeLotteryNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MarkSixLottery.class, FakeLotteryNumberRepository.class, PlayRuleScan.class})
class MarkSixLotteryTest {
    @Autowired
    MarkSixLottery markSixLottery;
    @MockBean
    NumberGenerator numberGenerator;

    @Test
    public void checkWin() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> guessNumbers = List.of(7);
        PlayParameter playParameter = PlayParameter.builder().position(7).build();

        PlayResult result = markSixLottery.checkNumbers(GameRuleEnum.GuessPositionN, winningNumbers, guessNumbers, playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    public void calculateNextRound() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);
        for (int i = 0; i < 59; i++) {
            long round = markSixLottery.calculateNextRound(localDateTime.plusMinutes(i));
            Assertions.assertEquals(20231229002L, round);
        }
    }

    @Test
    public void nextRoundCrossDay() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);

        long round = markSixLottery.calculateNextRound(startTime.plusHours(23));
        Assertions.assertEquals(20231230001L, round);
    }

    @Test
    public void generateNextRoundNumbers() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);

        boolean firstResult = markSixLottery.generateNextRoundNumbers(startTime).isPresent();
        Assertions.assertTrue(firstResult);

        boolean secondResult = markSixLottery.generateNextRoundNumbers(startTime).isEmpty();
        Assertions.assertTrue(secondResult);
    }

    @Test
    public void calculateNextRoundTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);
        for (int i = 0; i < 59; i++) {
            LocalDateTime result = markSixLottery.calculateNextRoundTime(localDateTime.plusMinutes(i));
            Assertions.assertEquals(LocalDateTime.of(2023, 12, 29, 1, 0, 0), result);
        }
    }

    @Test
    public void nextRoundTimeCrossDay() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);

        LocalDateTime result = markSixLottery.calculateNextRoundTime(startTime.plusHours(23));
        Assertions.assertEquals(LocalDateTime.of(2023, 12, 30, 0, 0, 0), result);
    }
}