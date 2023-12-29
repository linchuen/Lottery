package com.cooba.component.lottery;

import com.cooba.component.numberGenerator.SimpleNumberGenerator;
import com.cooba.config.PlayRuleScan;
import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.repository.FakeLotteryNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MarkSixLottery.class, SimpleNumberGenerator.class, FakeLotteryNumberRepository.class, PlayRuleScan.class})
class MarkSixLotteryTest {
    @Autowired
    private MarkSixLottery markSixLottery;

    @Test
    public void checkWin() {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6, 7);
        List<Integer> guessNumbers = List.of(7);
        PlayParameter playParameter = PlayParameter.builder().position(7).build();

        PlayResult result = markSixLottery.checkNumbers(GameRuleEnum.GuessPositionN, winningNumbers, guessNumbers, playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    void calculateNextRound() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);
        for (int i = 0; i < 59; i++) {
            long round = markSixLottery.calculateNextRound(localDateTime.plusMinutes(i));
            Assertions.assertEquals(20231229002L, round);
        }
    }

    @Test
    void calculateAllRound() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);
        int totalRound = markSixLottery.getLorreryRoundEnum().getTotalRound();

        long round = markSixLottery.calculateNextRound(startTime.plusHours(totalRound));
        Assertions.assertEquals(20231230002L, round);
    }
}