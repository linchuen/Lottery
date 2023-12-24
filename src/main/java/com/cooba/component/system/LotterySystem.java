package com.cooba.component.system;

import com.cooba.component.Lottery.Lottery;
import com.cooba.component.Lottery.LotteryFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.GameStatusEnum;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.GameInfo;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.SettleResult;
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
        GameRuleEnum gameRule = GameRuleEnum.getRuleById(gameRuleId);
        PlayResult playResult = lottery.checkNumbers(gameRule, winningNumbers, guessNumbers, playParameter);

        if (playResult.isTie()){
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
}
