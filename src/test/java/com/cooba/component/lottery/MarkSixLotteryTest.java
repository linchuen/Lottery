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
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> guessNumbers = List.of(6);
        PlayParameter playParameter = PlayParameter.builder().position(6).build();

        PlayResult result = markSixLottery.checkNumbers(GameRuleEnum.GuessPositionN, winningNumbers, guessNumbers, playParameter);
        Assertions.assertTrue(result.isWin());
    }

    @Test
    public void calculateNextRound() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);
        for (int i = 0; i < 29; i++) {
            long round = markSixLottery.calculateNextRound(localDateTime.plusMinutes(i));
            Assertions.assertEquals(20231229002L, round,"29號第1期後的時間為第2期");
        }
    }

    @Test
    public void nextRoundCrossDay() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 23, 30, 0);

        long round = markSixLottery.calculateNextRound(startTime);
        Assertions.assertEquals(20231230001L, round,"29號最後1期的下一期為30號的第1期");
    }

    @Test
    public void generateNextRoundNumbers() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);

        boolean firstResult = markSixLottery.generateNextRoundNumbers(startTime).isPresent();
        Assertions.assertTrue(firstResult,"第一次寫入號碼要成功");

        boolean secondResult = markSixLottery.generateNextRoundNumbers(startTime).isEmpty();
        Assertions.assertTrue(secondResult,"重複寫入號碼要失敗");
    }

    @Test
    public void calculateNextRoundTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 12, 29, 0, 0, 0);
        for (int i = 0; i < 29; i++) {
            LocalDateTime result = markSixLottery.calculateNextRoundTime(localDateTime.plusMinutes(i));
            Assertions.assertEquals(LocalDateTime.of(2023, 12, 29, 0, 30, 0), result);
        }
    }

    @Test
    public void nextRoundTimeCrossDay() {
        LocalDateTime startTime = LocalDateTime.of(2023, 12, 29, 23, 30, 0);

        LocalDateTime result = markSixLottery.calculateNextRoundTime(startTime);
        Assertions.assertEquals(LocalDateTime.of(2023, 12, 30, 0, 0, 0), result,"29號最後1期後為30號第1期開獎時間");
    }
}