package com.cooba.mapper;

import com.cooba.entity.OrderEntity;
import com.cooba.enums.OrderStatusEnum;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class OrderEntityMapperTest extends MapperTest{
    @Autowired
    OrderEntityMapper orderEntityMapper;

    @Test
    void insert() {
        OrderEntity orderEntity = Instancio.create(OrderEntity.class);
        orderEntityMapper.insertInitialOrder(orderEntity);
        Long id = orderEntity.getId();
        System.out.println(id);
        Assertions.assertNotNull(id);

        OrderEntity result = orderEntityMapper.selectByPrimaryKey(id).orElseThrow();
        System.out.println(result);
        Assertions.assertNull(result.getGuessNumbers());
    }

    @Test
    void update(){
        OrderEntity orderEntity = Instancio.create(OrderEntity.class);
        orderEntityMapper.insertInitialOrder(orderEntity);
        Long id = orderEntity.getId();
        System.out.println(id);
        Assertions.assertNotNull(id);

        orderEntityMapper.updateStatus(id, OrderStatusEnum.settle.getCode());
    }
}