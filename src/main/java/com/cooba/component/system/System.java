package com.cooba.component.system;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;

public interface System {
    SettleResult settleOrder(OrderEntity orderEntity);
}
