package com.cooba.component.system;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;

public interface Admin {
    SettleResult settleOrder(OrderEntity orderEntity);
}
