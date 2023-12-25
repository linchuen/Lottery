package com.cooba.component.admin;

import com.cooba.component.wallet.Wallet;
import com.cooba.config.LotteryScan;
import com.cooba.config.PlayRuleScan;
import com.cooba.config.WalletScan;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.ColorEnum;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.GameStatusEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.SettleResult;
import com.cooba.object.WinningNumberInfo;
import com.cooba.publisher.Publisher;
import com.cooba.repository.FakeOrderRepository;
import com.cooba.util.GameCodeUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LotterySystem.class, GameCodeUtility.class, FakeOrderRepository.class, PlayRuleScan.class, LotteryScan.class, WalletScan.class})
class LotterySystemTest {
    @Autowired
    LotterySystem lotterySystem;
    @Autowired
    GameCodeUtility gameCodeUtility;
    @MockBean
    Publisher publisher;
    @MockBean
    Wallet wallet;
    @Autowired
    FakeOrderRepository fakeOrderRepository;

    @BeforeEach
    public void testDataClear() {
        fakeOrderRepository.clear();
    }

    @Test
    void lotteryNotExist() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setGameCode("000" + "0000" + "00" + "2" + "2" + "00");
        Assertions.assertThrows(NoSuchElementException.class, () -> lotterySystem.calculateSettleResult(Collections.emptyList(), testOrder));
    }

    @Test
    void gameRuleNotExist() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setGameCode("001" + "0000" + "00" + "2" + "2" + "00");
        Assertions.assertThrows(NoSuchElementException.class, () -> lotterySystem.calculateSettleResult(Collections.emptyList(), testOrder));
    }

    @Test
    void testTieResult() {
        String lotteryCode = gameCodeUtility.getLotteryCode(LotteryEnum.MarkSix);
        String ruleCode = gameCodeUtility.getRuleCode(GameRuleEnum.PositionNTwoSide);
        String positionCode = gameCodeUtility.getPositionCode(1);

        OrderEntity testOrder = new OrderEntity();
        testOrder.setGameCode(lotteryCode + ruleCode + positionCode + "2" + "2" + "00");
        testOrder.setGuessNumbers(Collections.emptyList());
        List<Integer> winningNumbers = List.of(49, 1, 2, 3, 4, 5, 6);

        SettleResult settleResult = lotterySystem.calculateSettleResult(winningNumbers, testOrder);
        Assertions.assertEquals(GameStatusEnum.TIE.getCode(), settleResult.getGameStatus());
        Assertions.assertEquals(testOrder.getBetAmount(), settleResult.getBetPrize());
    }

    @Test
    void testWinResult() {
        String lotteryCode = gameCodeUtility.getLotteryCode(LotteryEnum.MarkSix);
        String ruleCode = gameCodeUtility.getRuleCode(GameRuleEnum.PositionNTwoSide);
        String positionCode = gameCodeUtility.getPositionCode(1);
        String isBigCode = gameCodeUtility.getIsBigCode(true);

        OrderEntity testOrder = new OrderEntity();
        testOrder.setGameCode(lotteryCode + ruleCode + positionCode + isBigCode + "2" + "00");
        testOrder.setGuessNumbers(Collections.emptyList());
        testOrder.setBetAmount(BigDecimal.TEN);
        testOrder.setOdds(new BigDecimal("2"));
        List<Integer> winningNumbers = List.of(48, 1, 2, 3, 4, 5, 6);

        SettleResult settleResult = lotterySystem.calculateSettleResult(winningNumbers, testOrder);
        Assertions.assertEquals(GameStatusEnum.WIN.getCode(), settleResult.getGameStatus());
        Assertions.assertEquals(new BigDecimal("19.80"), settleResult.getBetPrize());
    }

    @Test
    void testLoseResult() {
        String lotteryCode = gameCodeUtility.getLotteryCode(LotteryEnum.MarkSix);
        String ruleCode = gameCodeUtility.getRuleCode(GameRuleEnum.PositionNTwoSide);
        String positionCode = gameCodeUtility.getPositionCode(1);
        String isBigCode = gameCodeUtility.getIsBigCode(false);

        OrderEntity testOrder = new OrderEntity();
        testOrder.setGameCode(lotteryCode + ruleCode + positionCode + isBigCode + "2" + "00");
        testOrder.setGuessNumbers(Collections.emptyList());
        testOrder.setBetAmount(BigDecimal.TEN);
        testOrder.setOdds(new BigDecimal("2"));
        List<Integer> winningNumbers = List.of(48, 1, 2, 3, 4, 5, 6);

        SettleResult settleResult = lotterySystem.calculateSettleResult(winningNumbers, testOrder);
        Assertions.assertEquals(GameStatusEnum.LOSE.getCode(), settleResult.getGameStatus());
        Assertions.assertEquals(BigDecimal.ZERO, settleResult.getBetPrize());
    }

    @Test
    void settleOrdersWithError() {
        WinningNumberInfo winningNumberInfo = WinningNumberInfo.builder()
                .lotteryId(LotteryEnum.MarkSix.getId())
                .round(1)
                .winningNumbers(List.of(1, 2, 3, 4, 5, 6, 7))
                .build();

        String gameCode = gameCodeUtility.generate(LotteryEnum.MarkSix,
                GameRuleEnum.GuessPositionN,
                1, null, null, ColorEnum.RED);
        OrderEntity testOrder = new OrderEntity();
        testOrder.setId(1);
        testOrder.setRound(1);
        testOrder.setStatus(OrderStatusEnum.pay.getCode());
        testOrder.setGameCode(gameCode);
        testOrder.setGuessNumbers(List.of(1));
        fakeOrderRepository.putTestOrder(List.of(testOrder));

        lotterySystem.settleOrders(winningNumberInfo);

        OrderEntity result = fakeOrderRepository.selectOrderById(1).orElseThrow();
        Assertions.assertEquals(OrderStatusEnum.settleFailed.getCode(), result.getStatus());
    }

    @Test
    void settleOrdersSuccess() {
        WinningNumberInfo winningNumberInfo = WinningNumberInfo.builder()
                .lotteryId(LotteryEnum.MarkSix.getId())
                .round(1)
                .winningNumbers(List.of(1, 2, 3, 4, 5, 6, 7))
                .build();

        String gameCode = gameCodeUtility.generate(LotteryEnum.MarkSix,
                GameRuleEnum.GuessPositionN,
                1, null, null, ColorEnum.RED);
        OrderEntity testOrder = new OrderEntity();
        testOrder.setId(1);
        testOrder.setRound(1);
        testOrder.setStatus(OrderStatusEnum.pay.getCode());
        testOrder.setGameCode(gameCode);
        testOrder.setGuessNumbers(List.of(1));
        testOrder.setBetAmount(BigDecimal.TEN);
        testOrder.setOdds(BigDecimal.valueOf(2));
        fakeOrderRepository.putTestOrder(List.of(testOrder));

        lotterySystem.settleOrders(winningNumberInfo);

        Mockito.verify(publisher).publishEvent(Mockito.any());
        OrderEntity result = fakeOrderRepository.selectOrderById(1).orElseThrow();
        Assertions.assertEquals(OrderStatusEnum.settle.getCode(), result.getStatus());
    }

    @Test
    void sendLotteryPrize() {
    }
}