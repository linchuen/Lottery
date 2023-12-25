package com.cooba.component.order;

import com.cooba.entity.OrderEntity;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.repository.LotteryNumberRepository;
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
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class LotteryOrderTest {
    @InjectMocks
    LotteryOrder lotteryOrder;
    @Spy
    GameCodeUtility gameCodeUtility;
    @Mock
    LotteryNumberRepository lotteryNumberRepository;

    @Test
    public void checkWallet() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(0);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result);
    }

    @Test
    public void checkBetAmount() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setWalletId(WalletEnum.SIMPLE.getId());
        testOrder.setBetAmount(BigDecimal.ZERO);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result);
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
        testOrder.setWalletId(WalletEnum.SIMPLE.getId());
        testOrder.setBetAmount(BigDecimal.ONE);
        testOrder.setGameCode(gameCode);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result);
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
        testOrder.setWalletId(WalletEnum.SIMPLE.getId());
        testOrder.setBetAmount(BigDecimal.ONE);
        testOrder.setGameCode(gameCode);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result);
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
        testOrder.setWalletId(WalletEnum.SIMPLE.getId());
        testOrder.setBetAmount(BigDecimal.ONE);
        testOrder.setGameCode(gameCode);
        testOrder.setRound(1);
        Mockito.when(lotteryNumberRepository.getNextRound(LotteryEnum.MarkSix.getId())).thenReturn(2L);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertFalse(result);
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
        Mockito.when(lotteryNumberRepository.getNextRound(LotteryEnum.MarkSix.getId())).thenReturn(1L);

        boolean result = lotteryOrder.valid(testOrder);

        Assertions.assertTrue(result);
    }
}