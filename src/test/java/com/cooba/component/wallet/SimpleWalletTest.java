package com.cooba.component.wallet;

import com.cooba.exception.InsufficientBalanceException;
import com.cooba.exception.NoLockException;
import com.cooba.repository.FakePlayerWalletRepository;
import com.cooba.util.ReentrantLockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SimpleWallet.class, FakePlayerWalletRepository.class, ReentrantLockUtil.class})
class SimpleWalletTest {
    @Autowired
    SimpleWallet simpleWallet;
    @Autowired
    FakePlayerWalletRepository fakePlayerWalletRepository;

    @Test
    void depositAssetSingleThread() {
        for (int i = 0; i < 1000; i++) {
            simpleWallet.increaseAsset(1L, 1, BigDecimal.ONE);
        }
        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(1L, 1).orElseThrow();
        Assertions.assertEquals(new BigDecimal(1000), result);
    }

    @Test
    void depositAssetMultipleThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> completableFutureList = IntStream
                .range(0, 1000)
                .boxed()
                .map(integer -> {
                    Runnable runnable = () -> simpleWallet.increaseAsset(2L, 1, BigDecimal.ONE);
                    return CompletableFuture.runAsync(runnable, executorService);
                }).toList();
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(2L, 1).orElse(BigDecimal.ZERO);
        Assertions.assertEquals(new BigDecimal(1000), result);
    }

    @Test
    void withdrawAssetSingleThread() throws InsufficientBalanceException {
        fakePlayerWalletRepository.insertAssetAmount(3L,1,new BigDecimal(1000));

        for (int i = 0; i < 1000; i++) {
            simpleWallet.decreaseAsset(3L, 1, BigDecimal.ONE);
        }

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(3L, 1).orElseThrow();
        Assertions.assertEquals(new BigDecimal(0), result);
    }
}