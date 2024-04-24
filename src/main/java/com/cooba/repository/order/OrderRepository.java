package com.cooba.repository.order;

import com.cooba.entity.OrderEntity;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.SettleResult;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<OrderEntity> selectOrderById(long orderId);

    List<OrderEntity> selectOrderByStatus(int lotteryId, long round, OrderStatusEnum status);

    void updateSettleOrder(long orderId, SettleResult settleResult);

    void updateSettleFailOrder(long orderId);

    void updateAwardOrder(long orderId);

    long insertNewOrder(OrderEntity orderEntity);

    void updatePayOrder(long orderId);
}
