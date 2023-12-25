package com.cooba.repository;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<OrderEntity> selectOrderById(long orderId);

    List<OrderEntity> selectUnsettleOrder(int lotteryId, long round);

    void updateSettleOrder(long orderId, SettleResult settleResult);

    void updateSettleFailOrder(long orderId);

    void updateAwardOrder(long orderId);

    long insertNewOrder(OrderEntity orderEntity);

    void updatePayOrder(long orderId);
}
