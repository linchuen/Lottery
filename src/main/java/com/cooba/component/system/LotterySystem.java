package com.cooba.component.system;

import com.cooba.component.Lottery.Lottery;
import com.cooba.component.Lottery.LotteryFactory;
import com.cooba.component.Wallet.Wallet;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.GameStatusEnum;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.*;
import com.cooba.repository.OrderRepository;
import com.cooba.util.GameCodeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LotterySystem implements Admin {
    private final LotteryFactory lotteryFactory;
    private final GameCodeUtility gameCodeUtility;
    private final OrderRepository orderRepository;
    private final Wallet wallet;
    private static final BigDecimal feeRate = new BigDecimal("0.02");

    @Override
    public SettleResult calculateSettleResult(List<Integer> winningNumbers, OrderEntity orderEntity) {
        List<Integer> guessNumbers = orderEntity.getGuessNumbers();
        String gameCode = orderEntity.getGameCode();

        GameInfo gameInfo = gameCodeUtility.parse(gameCode);
        int lotteryId = gameInfo.getLotteryId();
        int gameRuleId = gameInfo.getGameRuleId();
        PlayParameter playParameter = gameInfo.getPlayParameter();

        Lottery lottery = lotteryFactory.getLottery(lotteryId).orElseThrow();
        GameRuleEnum gameRule = GameRuleEnum.getRuleById(gameRuleId).orElseThrow();
        PlayResult playResult = lottery.checkNumbers(gameRule, winningNumbers, guessNumbers, playParameter);

        if (playResult.isTie()) {
            return SettleResult.builder()
                    .fee(BigDecimal.ZERO)
                    .betPrize(orderEntity.getBetAmount())
                    .status(OrderStatusEnum.settle.getCode())
                    .gameStatus(GameStatusEnum.TIE.getCode())
                    .build();
        }

        if (!playResult.isWin()) {
            return SettleResult.builder()
                    .fee(BigDecimal.ZERO)
                    .betPrize(BigDecimal.ZERO)
                    .status(OrderStatusEnum.settle.getCode())
                    .gameStatus(GameStatusEnum.LOSE.getCode())
                    .build();
        }

        BigDecimal betAmount = orderEntity.getBetAmount();
        BigDecimal odds = orderEntity.getOdds();
        BigDecimal fee = betAmount.multiply(feeRate);
        BigDecimal betPrize = betAmount.multiply(odds).subtract(fee);

        return SettleResult.builder()
                .fee(fee)
                .betPrize(betPrize)
                .status(OrderStatusEnum.settle.getCode())
                .gameStatus(GameStatusEnum.WIN.getCode())
                .build();
    }

    @Override
    public void settleOrders(WinningNumberInfo winningNumberInfo) {
        int lotteryId = winningNumberInfo.getLotteryId();
        long round = winningNumberInfo.getRound();
        List<Integer> winningNumbers = winningNumberInfo.getWinningNumbers();

        List<OrderEntity> unsettledOrders = orderRepository.selectUnsettleOrder(lotteryId, round);
        for (OrderEntity unsettledOrder : unsettledOrders) {
            SettleResult settleResult = calculateSettleResult(winningNumbers, unsettledOrder);
            orderRepository.updateSettleOrder(settleResult);
        }
    }

    @Override
    public void sendLotteryPrize(int playerId, SettleResult settleResult) {
        wallet.increaseAsset();
    }
}
