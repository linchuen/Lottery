package com.cooba.repository;

import com.cooba.entity.OrderEntity;
import com.cooba.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class FakeOrderRepository implements OrderRepository {
    @Override
    public List<OrderEntity> selectUnsettleOrder(int lotteryId, long round) {
        return null;
    }
}
