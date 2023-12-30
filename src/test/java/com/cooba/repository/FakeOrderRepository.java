package com.cooba.repository;

import com.cooba.entity.OrderEntity;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.SettleResult;
import com.cooba.repository.order.OrderRepository;
import com.cooba.util.GameCodeUtility;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
public class FakeOrderRepository implements OrderRepository {
    private final Map<Long, OrderEntity> orderEntityMap = new HashMap<>();
    private final GameCodeUtility gameCodeUtility = new GameCodeUtility();

    public void clear() {
        orderEntityMap.clear();
    }

    public void putTestOrder(List<OrderEntity> testOrders) {
        testOrders.forEach(orderEntity -> orderEntityMap.put(orderEntity.getId(), orderEntity));
    }

    @Override
    public Optional<OrderEntity> selectOrderById(long orderId) {
        return Optional.ofNullable(orderEntityMap.get(orderId));
    }

    @Override
    public List<OrderEntity> selectUnsettleOrder(int lotteryId, long round) {
        return orderEntityMap.values().stream()
                .filter(orderEntity -> gameCodeUtility.parse(orderEntity.getGameCode()).getLotteryId() == lotteryId)
                .filter(orderEntity -> orderEntity.getRound() == round)
                .filter(orderEntity -> orderEntity.getStatus() == OrderStatusEnum.pay.getCode())
                .collect(Collectors.toList());
    }

    @Override
    public void updateSettleOrder(long orderId, SettleResult settleResult) {
        OrderEntity orderEntity = selectOrderById(orderId).orElseThrow();
        orderEntity.setFee(settleResult.getFee());
        orderEntity.setBetPrize(settleResult.getBetPrize());
        orderEntity.setGameStatus(settleResult.getGameStatus());
        orderEntity.setStatus(settleResult.getStatus());
        orderEntityMap.put(orderId, orderEntity);
    }

    @Override
    public void updateSettleFailOrder(long orderId) {
        OrderEntity orderEntity = selectOrderById(orderId).orElseThrow();
        orderEntity.setStatus(OrderStatusEnum.settleFailed.getCode());
        orderEntityMap.put(orderId, orderEntity);
    }

    @Override
    public void updateAwardOrder(long orderId) {
        OrderEntity orderEntity = selectOrderById(orderId).orElseThrow();
        orderEntity.setStatus(OrderStatusEnum.award.getCode());
        orderEntityMap.put(orderId, orderEntity);
    }

    @Override
    public long insertNewOrder(OrderEntity orderEntity) {
        long id = new Random().nextLong();
        orderEntity.setId(id);
        orderEntityMap.put(id, orderEntity);
        return id;
    }

    @Override
    public void updatePayOrder(long orderId) {
        OrderEntity orderEntity = selectOrderById(orderId).orElseThrow();
        orderEntity.setStatus(OrderStatusEnum.pay.getCode());
        orderEntityMap.put(orderId, orderEntity);
    }

    @Override
    public void updateCancelOrder(long orderId) {
        OrderEntity orderEntity = selectOrderById(orderId).orElseThrow();
        orderEntity.setStatus(OrderStatusEnum.cancel.getCode());
        orderEntityMap.put(orderId, orderEntity);
    }
}
