package com.cooba;

import com.cooba.component.lottery.MarkSixLottery;
import com.cooba.component.numberGenerator.NumberGenerator;
import com.cooba.enums.AssetEnum;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.repository.lotteryNumber.LotteryNumberRepository;
import com.cooba.request.BetRequest;
import com.cooba.util.GameCodeUtility;
import com.cooba.util.JsonUtil;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


public class ForBuildBetRequestTest {
    public static void main(String[] args) {
        MarkSixLottery markSixLottery = new MarkSixLottery(
                Collections.emptyList(),
                Mockito.mock(NumberGenerator.class),
                Mockito.mock(LotteryNumberRepository.class));
        long nextRound = markSixLottery.calculateNextRound(LocalDateTime.now());

        GameCodeUtility gameCodeUtility = new GameCodeUtility();
        String gameCode = gameCodeUtility.generate(
                LotteryEnum.MarkSix,
                GameRuleEnum.SelectN,
                null,
                null,
                null,
                null);

        BetRequest betRequest = new BetRequest();
        betRequest.setPlayerId(89);
        betRequest.setWalletId(WalletEnum.SIMPLE.getId());
        betRequest.setAssetId(AssetEnum.TWD.getId());
        betRequest.setGameCode(gameCode);
        betRequest.setRound(nextRound);
        betRequest.setGuessNumbers(List.of(2));
        betRequest.setBetAmount(BigDecimal.valueOf(20));
        System.out.println(JsonUtil.toJsonString(betRequest));
    }
}
