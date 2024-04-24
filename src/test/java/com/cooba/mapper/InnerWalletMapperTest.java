package com.cooba.mapper;

import com.cooba.entity.SimpleWalletEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InnerWalletMapperTest extends MapperTest{
    @Autowired
    SimpleWalletMapper simpleWalletMapper;

    @Test
    void insert() {
        long playerId = Instancio.create(Long.class);
        int assetId = Instancio.create(Integer.class);
        BigDecimal amount = Instancio.create(BigDecimal.class);

        simpleWalletMapper.insertAsset(playerId, assetId, amount);

        SimpleWalletEntity result = simpleWalletMapper.selectPlayerAsset(playerId, assetId).orElseThrow();
        System.out.println(result);
        assertEquals(0, result.getAmount().compareTo(amount));
    }

    @Test
    void update() {
        long playerId = Instancio.create(Long.class);
        int assetId = Instancio.create(Integer.class);
        BigDecimal amount = Instancio.create(BigDecimal.class);

        simpleWalletMapper.insertAsset(playerId, assetId, amount);
        simpleWalletMapper.updateAsset(playerId, assetId, BigDecimal.TEN);


        SimpleWalletEntity result = simpleWalletMapper.selectPlayerAsset(playerId, assetId).orElseThrow();
        System.out.println(result);
        assertEquals(0, result.getAmount().compareTo(BigDecimal.TEN));
    }
}