package com.cooba.component.order;

import com.cooba.entity.OrderEntity;
import com.cooba.request.BetRequest;

public interface Order {

    OrderEntity generate(BetRequest betRequest);

    boolean valid(OrderEntity orderEntity);
}
