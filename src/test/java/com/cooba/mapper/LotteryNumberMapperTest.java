package com.cooba.mapper;

import com.cooba.config.DataSourceConfig;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataSourceConfig.class)
class LotteryNumberMapperTest {
    @Autowired
    LotteryNumberMapper lotteryNumberMapper;

    @Test
    void insertNumbers() {
        int result = lotteryNumberMapper.insertNumbers(Instancio.create(Integer.class), Instancio.create(Long.class), "[41,,42,43,44,45,46,47]");
        System.out.println(result);
        Assertions.assertEquals(1, result);
    }
}