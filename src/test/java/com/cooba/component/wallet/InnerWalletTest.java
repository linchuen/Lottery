package com.cooba.component.wallet;

import com.cooba.config.RedissonConfig;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.repository.FakePlayerWalletRepository;
import com.cooba.util.RedissonLockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {InnerWallet.class, FakePlayerWalletRepository.class, RedissonLockUtil.class, RedissonConfig.class})
class InnerWalletTest {
    @Autowired
    InnerWallet innerWallet;
    @Autowired
    FakePlayerWalletRepository fakePlayerWalletRepository;

    @Test
    @DisplayName("單線程儲值")
    void depositAssetSingleThread() {
        for (int i = 0; i < 1000; i++) {
            innerWallet.increaseAsset(1L, 1, BigDecimal.ONE);
        }
        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(1L, 1).orElseThrow();
        Assertions.assertEquals(new BigDecimal(1000), result);
    }

    @Test
    @DisplayName("多線程儲值")
    void depositAssetMultipleThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> completableFutureList = IntStream
                .range(0, 1000)
                .boxed()
                .map(i -> CompletableFuture.runAsync(
                        () -> innerWallet.increaseAsset(2L, 1, BigDecimal.ONE),
                        executorService))
                .toList();
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(2L, 1).orElse(BigDecimal.ZERO);
        Assertions.assertEquals(new BigDecimal(1000), result);
    }

    @Test
    @DisplayName("單線程提款")
    void withdrawAssetSingleThread() throws InsufficientBalanceException {
        fakePlayerWalletRepository.insertAssetAmount(3L, 1, new BigDecimal(1000));

        for (int i = 0; i < 1000; i++) {
            innerWallet.decreaseAsset(3L, 1, BigDecimal.ONE);
        }

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(3L, 1).orElseThrow();
        Assertions.assertEquals(new BigDecimal(0), result);
    }

    @Test
    @DisplayName("多線程提款")
    void withdrawAssetMultipleThread() {
        fakePlayerWalletRepository.insertAssetAmount(4L, 1, new BigDecimal(1000));
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> completableFutureList = IntStream
                .range(0, 1000)
                .boxed()
                .map(i -> CompletableFuture.runAsync(
                        () -> innerWallet.decreaseAsset(4L, 1, BigDecimal.ONE),
                        executorService))
                .toList();
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(4L, 1).orElse(BigDecimal.ZERO);
        Assertions.assertEquals(new BigDecimal(0), result);
    }

    @Test
    void withdrawAssetWithNoWalletAndInsufficientBalance() {
        fakePlayerWalletRepository.insertAssetAmount(5L, 1, new BigDecimal(0));
        Assertions.assertThrows(InsufficientBalanceException.class,
                () -> innerWallet.decreaseAsset(5L, 1, BigDecimal.ONE));
    }
}