package com.cooba.mapper;

import com.cooba.config.DataSourceConfig;
import com.cooba.entity.SimpleWalletEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataSourceConfig.class)
class SimpleWalletMapperTest {
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
        Assertions.assertTrue(result.getAmount().compareTo(amount) == 0);
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
        Assertions.assertTrue(result.getAmount().compareTo(BigDecimal.TEN) == 0);
    }
}