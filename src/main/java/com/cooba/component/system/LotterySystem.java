package com.cooba.component.system;

import com.cooba.component.Lottery.Lottery;
import com.cooba.component.Lottery.LotteryFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.object.GameInfo;
import com.cooba.object.SettleResult;
import com.cooba.util.GameCodeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LotterySystem implements Admin {
    private final LotteryFactory lotteryFactory;
    private final GameCodeUtility gameCodeUtility;

    @Override
    public SettleResult settleOrder(OrderEntity orderEntity) {
        String gameCode = orderEntity.getGameCode();
        GameInfo gameInfo = gameCodeUtility.parse(gameCode);
        Lottery lottery = lotteryFactory.getLottery(gameInfo.getLotteryId()).orElseThrow();
        return null;
    }
}
