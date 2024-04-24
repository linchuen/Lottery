package com.cooba.component.wallet;

import com.cooba.exception.InsufficientBalanceException;
import com.cooba.repository.FakePlayerWalletRepository;
import com.cooba.util.ReentrantLockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.IntStream;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@ContextConfiguration(classes = {SimpleWallet.class, FakePlayerWalletRepository.class, RedissonLockUtil.class, RedissonConfig.class})
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
        fakePlayerWalletRepository.insertAssetAmount(3L, 1, new BigDecimal(1000));

        for (int i = 0; i < 1000; i++) {
            simpleWallet.decreaseAsset(3L, 1, BigDecimal.ONE);
        }

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(3L, 1).orElseThrow();
        Assertions.assertEquals(new BigDecimal(0), result);
    }

    @Test
    void withdrawAssetMultipleThread() {
        fakePlayerWalletRepository.insertAssetAmount(4L, 1, new BigDecimal(1000));
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> completableFutureList = IntStream
                .range(0, 1000)
                .boxed()
                .map(integer -> {
                    Runnable runnable = () -> {
                        simpleWallet.decreaseAsset(4L, 1, BigDecimal.ONE);
                    };
                    return CompletableFuture.runAsync(runnable, executorService);
                }).toList();
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(4L, 1).orElse(BigDecimal.ZERO);
        Assertions.assertEquals(new BigDecimal(0), result);
    }

    @Test
    void withdrawAssetMultipleThreadWithInsufficientBalance() {
        fakePlayerWalletRepository.insertAssetAmount(4L, 1, new BigDecimal(1000));
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Boolean>> completableFutureList = IntStream
                .range(0, 1500)
                .boxed()
                .map(integer -> {
                    Supplier<Boolean> supplier = () -> {
                        simpleWallet.decreaseAsset(4L, 1, BigDecimal.ONE);
                        return true;
                    };
                    return CompletableFuture.supplyAsync(supplier, executorService);
                }).toList();
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0])).join();

        BigDecimal result = fakePlayerWalletRepository.selectAssetAmount(4L, 1).orElse(BigDecimal.ZERO);
        Assertions.assertEquals(new BigDecimal(0), result);

        long successTime = completableFutureList.stream().filter(c -> {
            try {
                return c.get();
            } catch (InterruptedException | ExecutionException e) {
                return false;
            }
        }).count();
        Assertions.assertEquals(1000, successTime);
    }

    @Test
    void withdrawAssetWithNoWalletAndInsufficientBalance() {
        fakePlayerWalletRepository.insertAssetAmount(5L, 1, new BigDecimal(0));

        Executable executable = () -> simpleWallet.decreaseAsset(5L, 1, BigDecimal.ONE);

        Assertions.assertThrows(InsufficientBalanceException.class, executable);
    }
}