package com.cooba.mapper;

import com.cooba.config.DataSourceConfig;
import com.cooba.entity.OrderEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataSourceConfig.class)
class OrderEntityMapperTest {
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
        Assertions.assertNotNull(result.getGuessNumbers());
    }
}