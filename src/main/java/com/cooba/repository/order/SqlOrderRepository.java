package com.cooba.repository.order;

import com.cooba.entity.OrderEntity;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.mapper.OrderEntityMapper;
import com.cooba.object.SettleResult;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SqlOrderRepository implements OrderRepository {
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public Optional<OrderEntity> selectOrderById(long orderId) {
        Optional<OrderEntity> orderEntity = orderEntityMapper.selectByPrimaryKey(orderId);
        if (orderEntity.isPresent()) {
            OrderEntity order = orderEntity.get();
            order.setGuessNumbers(JsonUtil.parseList(order.getGuessString(), Integer.class));
        }
        return orderEntity;
    }

    @Override
    public List<OrderEntity> selectOrderByStatus(int lotteryId, long round, OrderStatusEnum status) {
        List<OrderEntity> unsettleOrders = orderEntityMapper.selectLotteryByStatus(lotteryId, round, OrderStatusEnum.pay.getCode());
        for (OrderEntity unsettleOrder : unsettleOrders) {
            unsettleOrder.setGuessNumbers(JsonUtil.parseList(unsettleOrder.getGuessString(), Integer.class));
        }
        return unsettleOrders;
    }

    @Override
    public void updateSettleOrder(long orderId, SettleResult settleResult) {
        orderEntityMapper.updateSettleResult(orderId, settleResult);
    }

    @Override
    public void updateSettleFailOrder(long orderId) {
        orderEntityMapper.updateStatus(orderId, OrderStatusEnum.settleFailed.getCode());
    }

    @Override
    public void updateAwardOrder(long orderId) {
        orderEntityMapper.updateStatus(orderId, OrderStatusEnum.award.getCode());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public long insertNewOrder(OrderEntity orderEntity) {
        orderEntity.setGuessString(JsonUtil.toJsonString(orderEntity.getGuessNumbers()));
        orderEntityMapper.insertInitialOrder(orderEntity);
        return orderEntity.getId();
    }

    @Override
    public void updatePayOrder(long orderId) {
        orderEntityMapper.updateStatus(orderId, OrderStatusEnum.pay.getCode());
    }

}
