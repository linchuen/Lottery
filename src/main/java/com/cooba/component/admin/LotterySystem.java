package com.cooba.component.admin;

import com.cooba.component.lottery.Lottery;
import com.cooba.component.lottery.LotteryFactory;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.GameStatusEnum;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.GameInfo;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.SettleResult;
import com.cooba.object.WinningNumberInfo;
import com.cooba.repository.order.OrderRepository;
import com.cooba.util.GameCodeUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotterySystem implements Admin {
    private final LotteryFactory lotteryFactory;
    private final GameCodeUtility gameCodeUtility;
    private final WalletFactory walletFactory;
    private final OrderRepository orderRepository;
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

        List<OrderEntity> unsettledOrders = orderRepository.selectOrderByStatus(lotteryId, round, OrderStatusEnum.pay);
        for (OrderEntity unsettledOrder : unsettledOrders) {
            long orderId = unsettledOrder.getId();
            try {
                SettleResult settleResult = calculateSettleResult(winningNumbers, unsettledOrder);
                orderRepository.updateSettleOrder(orderId, settleResult);
            } catch (Exception e) {
                log.error("結算錯誤 orderId:{} :{}", orderId, e.getMessage());
                orderRepository.updateSettleFailOrder(orderId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void sendLotteryPrize(OrderEntity settledOrder) {
        if (settledOrder.getStatus() != OrderStatusEnum.settle.getCode()) return;

        int walletId = settledOrder.getWalletId();
        long playerId = settledOrder.getPlayerId();
        int assetId = settledOrder.getAssetId();
        BigDecimal betPrize = settledOrder.getBetPrize();

        Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();
        if (betPrize.compareTo(BigDecimal.ZERO) > 0) {
            wallet.increaseAsset(playerId, assetId, betPrize);
        }
        orderRepository.updateAwardOrder(settledOrder.getId());
    }
}
