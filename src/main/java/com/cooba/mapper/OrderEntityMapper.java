package com.cooba.mapper;

import com.cooba.entity.OrderEntity;
import com.cooba.object.SettleResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface OrderEntityMapper {
    Optional<OrderEntity> selectByPrimaryKey(@Param("id") long id);

    List<OrderEntity> selectLotteryByStatus(@Param("lotteryId") int lotteryId,
                                            @Param("round") long round,
                                            @Param("status") int status);

    void updateSettleResult(@Param("id") long orderId, @Param("result") SettleResult settleResult);

    void updateStatus(@Param("id") long orderId, @Param("status") int status);

    Long insertInitialOrder(@Param("order") OrderEntity orderEntity);
}
