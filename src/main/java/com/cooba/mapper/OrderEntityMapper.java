package com.cooba.mapper;

import com.cooba.entity.OrderEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface OrderEntityMapper {
    Optional<OrderEntity> selectByPrimaryKey(@Param("id") long id);
}
