package com.cooba;

import com.cooba.component.lottery.MarkSixLottery;
import com.cooba.component.numberGenerator.NumberGenerator;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.repository.lotteryNumber.LotteryNumberRepository;
import com.cooba.util.GameCodeUtility;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Collections;


public class ForBuildBetRequestTest {
    public static void main(String[] args) {
        MarkSixLottery markSixLottery = new MarkSixLottery(
                Collections.emptyList(),
                Mockito.mock(NumberGenerator.class),
                Mockito.mock(LotteryNumberRepository.class));
        long nextRound = markSixLottery.calculateNextRound(LocalDateTime.now());
        System.out.println(nextRound);

        GameCodeUtility gameCodeUtility = new GameCodeUtility();
        String gameCode = gameCodeUtility.generate(
                LotteryEnum.MarkSix,
                GameRuleEnum.GuessPositionN,
                2,
                null,
                null,
                null);
        System.out.println(gameCode);
    }
}
