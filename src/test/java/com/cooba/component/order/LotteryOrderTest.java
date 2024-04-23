package com.cooba.component.order;

import com.cooba.component.lottery.Lottery;
import com.cooba.component.lottery.LotteryFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.request.BetRequest;
import com.cooba.util.GameCodeUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class LotteryOrderTest {
    @InjectMocks
    LotteryOrder lotteryOrder;
    @Spy
    GameCodeUtility gameCodeUtility;
    @Mock
    LotteryFactory lotteryFactory;
    @Mock
    Lottery lottery;

    @Test
    public void checkWallet() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(0);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result,"Wallet不匹配");
    }

    @Test
    public void checkBetAmount() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(WalletEnum.INNER.getId());
        testOrder.setBetAmount(BigDecimal.ZERO);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result,"BetAmount應大於0");
    }

    @Test
    public void checkLottery() {
        String gameCode = gameCodeUtility.generate(
                null,
                GameRuleEnum.GuessPositionN,
                0,
                null, null, null
        );
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(WalletEnum.INNER.getId());
        testOrder.setBetAmount(BigDecimal.ONE);
        testOrder.setGameCode(gameCode);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result,"Lottery不得為空");
    }

    @Test
    public void checkGameRule() {
        String gameCode = gameCodeUtility.generate(
                LotteryEnum.MarkSix,
                null,
                0,
                null, null, null
        );
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(WalletEnum.INNER.getId());
        testOrder.setBetAmount(BigDecimal.ONE);
        testOrder.setGameCode(gameCode);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result,"GameRule不得為空");
    }

    @Test
    public void checkRound() {
        String gameCode = gameCodeUtility.generate(
                LotteryEnum.MarkSix,
                GameRuleEnum.GuessPositionN,
                0,
                null, null, null
        );
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(WalletEnum.INNER.getId());
        testOrder.setBetAmount(BigDecimal.ONE);
        testOrder.setGameCode(gameCode);
        testOrder.setRound(1);
        Mockito.when(lotteryFactory.getLottery(LotteryEnum.MarkSix.getId())).thenReturn(Optional.ofNullable(lottery));
        Mockito.when(lottery.calculateNextRound(any(LocalDateTime.class))).thenReturn(2L);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result,"測試期數應不相等");
    }


    @Test
    void checkValidBetTime() {
        BetRequest betRequest = new BetRequest();
        betRequest.setPlayerId(1);
        betRequest.setWalletId(1);
        betRequest.setAssetId(1);
        betRequest.setRound(1);
        betRequest.setGameCode(gameCodeUtility.generate(
                LotteryEnum.MarkSix,
                GameRuleEnum.GuessPositionN,
                0,
                null, null, null
        ));
        betRequest.setGuessNumbers(Collections.emptyList());
        betRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = lotteryOrder.generate(betRequest);
        Mockito.when(lotteryFactory.getLottery(LotteryEnum.MarkSix.getId())).thenReturn(Optional.ofNullable(lottery));
        Mockito.when(lottery.calculateNextRound(any(LocalDateTime.class))).thenReturn(1L);
        Mockito.when(lottery.calculateNextRoundTime(any(LocalDateTime.class))).thenReturn(LocalDateTime.now().plusSeconds(10));

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result,"下注結束前10秒為封盤時間");
    }

    @Test
    void valid() {
        BetRequest betRequest = new BetRequest();
        betRequest.setPlayerId(1);
        betRequest.setWalletId(1);
        betRequest.setAssetId(1);
        betRequest.setRound(1);
        betRequest.setGameCode(gameCodeUtility.generate(
                LotteryEnum.MarkSix,
                GameRuleEnum.GuessPositionN,
                0,
                null, null, null
        ));
        betRequest.setGuessNumbers(Collections.emptyList());
        betRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = lotteryOrder.generate(betRequest);
        Mockito.when(lotteryFactory.getLottery(LotteryEnum.MarkSix.getId())).thenReturn(Optional.ofNullable(lottery));
        Mockito.when(lottery.calculateNextRound(any(LocalDateTime.class))).thenReturn(1L);
        Mockito.when(lottery.calculateNextRoundTime(any(LocalDateTime.class))).thenReturn(LocalDateTime.now().plusSeconds(11));

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertTrue(result);
    }
}