package com.cooba.mapper;

import com.cooba.config.DataSourceConfig;
import com.cooba.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataSourceConfig.class)
class OrderEntityMapperTest {
    @Autowired
    OrderEntityMapper orderEntityMapper;

    @Test
    void selectByPrimaryKey() {
        Optional<OrderEntity> orderEntity = orderEntityMapper.selectByPrimaryKey(1L);
        System.out.println(orderEntity);
    }
}