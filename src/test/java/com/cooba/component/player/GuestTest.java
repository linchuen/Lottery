package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.AssetEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.repository.FakeOrderRepository;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.util.MapLockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@ContextConfiguration(classes = {Guest.class, FakeOrderRepository.class, RedissonLockUtil.class, RedissonConfig.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Guest.class, FakeOrderRepository.class, MapLockUtil.class})
class GuestTest {
    @Autowired
    Guest guest;
    @MockBean
    Order order;
    @MockBean
    WalletFactory walletFactory;
    @MockBean
    Wallet wallet;

    @Test
    public void betWithOrderNotValid() {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setPlayerId(1);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(false);

        BetResult betResult = guest.bet(testBetRequest);

        Assertions.assertFalse(betResult.isSuccess());
        Assertions.assertEquals("驗證失敗", betResult.getErrorMessage());
    }

    @Test
    public void betWithDecreaseMoneyError() throws Exception {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setPlayerId(2);
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));
        Mockito.doThrow(new InsufficientBalanceException())
                .when(wallet).decreaseAsset(anyLong(), anyInt(), any(BigDecimal.class));

        BetResult betResult = guest.bet(testBetRequest);

        Assertions.assertFalse(betResult.isSuccess());
        Assertions.assertNotEquals("驗證失敗", betResult.getErrorMessage());
    }

    @Test
    public void betSuccess() {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setPlayerId(3);
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));

        BetResult betResult = guest.bet(testBetRequest);

        Assertions.assertTrue(betResult.isSuccess());
        Assertions.assertNull(betResult.getErrorMessage());
    }

    @Test
    public void betTwiceContinuously() throws ExecutionException, InterruptedException {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setPlayerId(4);
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        BetResult betResult1 = CompletableFuture.supplyAsync(() -> guest.bet(testBetRequest), executorService).get();
        BetResult betResult2 = CompletableFuture.supplyAsync(() -> guest.bet(testBetRequest), executorService).get();
        Thread.sleep(3000);
        BetResult betResult3 = CompletableFuture.supplyAsync(() -> guest.bet(testBetRequest), executorService).get();

        Assertions.assertTrue(betResult1.isSuccess());
        Assertions.assertFalse(betResult2.isSuccess());
        Assertions.assertTrue(betResult3.isSuccess());
    }

    @Test
    void create() {
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));

        CreatePlayerResult result = guest.create(new CreatePlayerRequest());

        Assertions.assertEquals(WalletEnum.INNER.getId(), result.getWalletId());
        Assertions.assertEquals(AssetEnum.TWD.getId(), result.getAssetId());
        Assertions.assertEquals(BigDecimal.valueOf(2000), result.getAmount());
    }
}