package com.cooba.component.wallet;

import com.cooba.repository.FakePlayerWalletRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SimpleWallet.class, FakePlayerWalletRepository.class})
class SimpleWalletTest {
    @Autowired
    SimpleWallet simpleWallet;
    @Autowired
    FakePlayerWalletRepository fakePlayerWalletRepository;

    @Test
    void depositAsset() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 1000; i++) {
            CompletableFuture.runAsync(() -> simpleWallet.increaseAsset(1L, 1, BigDecimal.ONE), executorService);
        }
        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(1L, 1).orElseThrow();
        System.out.println(result);
    }

    @Test
    void withdrawAsset() {
    }
}