package com.cooba.repository;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;

import java.util.List;

public interface OrderRepository {
    List<OrderEntity> selectUnsettleOrder(int lotteryId, long round);

    void updateSettleOrder(SettleResult settleResult);
}
