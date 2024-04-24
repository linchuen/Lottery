package com.cooba.component.order;

import com.cooba.component.lottery.Lottery;
import com.cooba.component.lottery.LotteryFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.*;
import com.cooba.object.GameInfo;
import com.cooba.request.BetRequest;
import com.cooba.util.GameCodeUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LotteryOrder implements Order {
    private final LotteryFactory lotteryFactory;
    private final GameCodeUtility gameCodeUtility;

    @Override
    public OrderEntity generate(BetRequest betRequest) {
        String gameCode = betRequest.getGameCode();
        int lotteryId = gameCodeUtility.getLotteryId(gameCode);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(betRequest.getOrderNo());
        orderEntity.setPlayerId(betRequest.getPlayerId());
        orderEntity.setWalletId(betRequest.getWalletId());
        orderEntity.setAssetId(betRequest.getAssetId());
        orderEntity.setLotteryId(lotteryId);
        orderEntity.setRound(betRequest.getRound());
        orderEntity.setGameCode(gameCode);
        orderEntity.setGuessNumbers(betRequest.getGuessNumbers());
        orderEntity.setBetAmount(betRequest.getBetAmount());
        orderEntity.setStatus(OrderStatusEnum.create.getCode());
        return orderEntity;
    }

    @Override
    public boolean valid(OrderEntity orderEntity) {
        if (!checkWallet(orderEntity)) return false;

        if (!checkBetAmount(orderEntity)) return false;

        GameInfo gameInfo;
        try {
            gameInfo = gameCodeUtility.parse(orderEntity.getGameCode());
        } catch (Exception e) {
            log.error("{} {}", this.getClass().getName(), e.getMessage());
            return false;
        }

        if (!checkLottery(gameInfo)) return false;

        if (!checkGameRule(gameInfo)) return false;

        if (!checkRound(orderEntity.getRound(), gameInfo)) return false;

        return checkValidBetTime(gameInfo);
    }

    private static boolean checkWallet(OrderEntity orderEntity) {
        return WalletEnum.getWalletById(orderEntity.getWalletId()).isPresent();
    }

    private static boolean checkBetAmount(OrderEntity orderEntity) {
        return orderEntity.getBetAmount().compareTo(BigDecimal.ZERO) > 0;
    }

    private static boolean checkLottery(GameInfo gameInfo) {
        return LotteryEnum.getLotteryById(gameInfo.getLotteryId()).isPresent();
    }

    private static boolean checkGameRule(GameInfo gameInfo) {
        return GameRuleEnum.getRuleById(gameInfo.getGameRuleId()).isPresent();
    }

    private boolean checkRound(long round, GameInfo gameInfo) {
        Lottery lottery = lotteryFactory.getLottery(gameInfo.getLotteryId()).orElseThrow();
        long nextRound = lottery.calculateNextRound(LocalDateTime.now());
        return round == nextRound;
    }

    private boolean checkValidBetTime(GameInfo gameInfo){
        Lottery lottery = lotteryFactory.getLottery(gameInfo.getLotteryId()).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRoundTime = lottery.calculateNextRoundTime(LocalDateTime.now());
        return now.isBefore(nextRoundTime.minusSeconds(10));
    }
}
