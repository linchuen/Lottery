package com.cooba.mapper;

import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class LotteryNumberMapperTest extends MapperTest{
    @Autowired
    LotteryNumberMapper lotteryNumberMapper;

    @Test
    void insertNumbers() {
        int result = lotteryNumberMapper.insertNumbers(Instancio.create(Integer.class), Instancio.create(Long.class), "[41,,42,43,44,45,46,47]");
        System.out.println(result);
        Assertions.assertEquals(1, result);
    }
}