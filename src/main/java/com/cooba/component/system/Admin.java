package com.cooba.component.system;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;

import java.util.List;

public interface Admin {
    SettleResult calculateSettleResult(List<Integer> winningNumbers, OrderEntity orderEntity);
}
