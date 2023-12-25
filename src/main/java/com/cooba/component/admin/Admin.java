package com.cooba.component.admin;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;
import com.cooba.object.WinningNumberInfo;

import java.util.List;

public interface Admin {
    SettleResult calculateSettleResult(List<Integer> winningNumbers, OrderEntity orderEntity);

    void settleOrders(WinningNumberInfo winningNumberInfo);

    void sendLotteryPrize(long orderId);
}
