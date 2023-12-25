package com.cooba.repository;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<OrderEntity> selectOrderById(long orderId);

    List<OrderEntity> selectUnsettleOrder(int lotteryId, long round);

    void updateSettleOrder(SettleResult settleResult);

    void updateAwardOrder(long orderId);
}
