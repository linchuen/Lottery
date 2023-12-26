package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.object.BetResult;
import com.cooba.repository.FakeOrderRepository;
import com.cooba.request.BetRequest;
import com.cooba.util.MapLockUntil;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {Guest.class, FakeOrderRepository.class, MapLockUntil.class})
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
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(false);

        BetResult betResult = guest.bet(1, testBetRequest);

        Assertions.assertFalse(betResult.isSuccess());
        Assertions.assertEquals("驗證失敗", betResult.getErrorMessage());
    }

    @Test
    public void betWithDecreaseMoneyError() {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));
        Mockito.doThrow(new RuntimeException())
                .when(wallet).decreaseAsset(anyLong(), anyInt(), any(BigDecimal.class));

        BetResult betResult = guest.bet(1, testBetRequest);

        Assertions.assertFalse(betResult.isSuccess());
        Assertions.assertNotEquals("驗證失敗", betResult.getErrorMessage());
    }

    @Test
    public void betSuccess() {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));

        BetResult betResult = guest.bet(1, testBetRequest);

        Assertions.assertTrue(betResult.isSuccess());
        Assertions.assertNull(betResult.getErrorMessage());
    }

    @Test
    public void betTwiceAtSameTime() {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.valid(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));

        BetResult betResult1 = guest.bet(1, testBetRequest);
        BetResult betResult2 = guest.bet(1, testBetRequest);

        Assertions.assertFalse(betResult1.isSuccess() && betResult2.isSuccess());
    }
}