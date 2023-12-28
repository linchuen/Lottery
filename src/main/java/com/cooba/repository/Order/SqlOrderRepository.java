package com.cooba.repository.Order;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SqlOrderRepository implements OrderRepository {
    @Override
    public Optional<OrderEntity> selectOrderById(long orderId) {
        return Optional.empty();
    }

    @Override
    public List<OrderEntity> selectUnsettleOrder(int lotteryId, long round) {
        return null;
    }

    @Override
    public void updateSettleOrder(long orderId, SettleResult settleResult) {

    }

    @Override
    public void updateSettleFailOrder(long orderId) {

    }

    @Override
    public void updateAwardOrder(long orderId) {

    }

    @Override
    public long insertNewOrder(OrderEntity orderEntity) {
        return 0;
    }

    @Override
    public void updatePayOrder(long orderId) {

    }

    @Override
    public void updateCancelOrder(long orderId) {

    }
}
