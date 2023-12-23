package com.cooba.component.system;

import com.cooba.component.Lottery.Lottery;
import com.cooba.component.Lottery.LotteryFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.object.GameInfo;
import com.cooba.object.SettleResult;
import com.cooba.util.GameCodeParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LotterySystem implements System {
    private final LotteryFactory lotteryFactory;
    private final GameCodeParser gameCodeParser;

    @Override
    public SettleResult settleOrder(OrderEntity orderEntity) {
        String gameCode = orderEntity.getGameCode();
        GameInfo gameInfo = gameCodeParser.parse(gameCode);
        Lottery lottery = lotteryFactory.getLottery(gameInfo.getLotteryId()).orElseThrow();
        return null;
    }
}
