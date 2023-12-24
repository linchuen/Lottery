package com.cooba.component.system;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;
import com.cooba.object.WinningNumberInfo;

import java.util.List;

public interface Admin {
    SettleResult calculateSettleResult(List<Integer> winningNumbers, OrderEntity orderEntity);

    void settleOrders(WinningNumberInfo winningNumberInfo);

    void sendLotteryPrize(int playerId, SettleResult settleResult);
}
