package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.config.RedissonConfig;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.AssetEnum;
import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.repository.FakeOrderRepository;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.util.RedissonLockUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {Guest.class, FakeOrderRepository.class, RedissonLockUtil.class, RedissonConfig.class})
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
        Mockito.when(order.verify(any(OrderEntity.class))).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> guest.bet(testBetRequest));
    }

    @Test
    public void betWithDecreaseMoneyError() {
        BetRequest testBetRequest = new BetRequest();
        testBetRequest.setPlayerId(2);
        testBetRequest.setWalletId(1);
        testBetRequest.setAssetId(1);
        testBetRequest.setBetAmount(BigDecimal.ONE);
        OrderEntity testOrder = new OrderEntity();

        Mockito.when(order.generate(testBetRequest)).thenReturn(testOrder);
        Mockito.when(order.verify(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));
        Mockito.doThrow(new InsufficientBalanceException())
                .when(wallet).decreaseAsset(anyLong(), anyInt(), any(BigDecimal.class));

        Assertions.assertThrows(RuntimeException.class, () -> guest.bet(testBetRequest));
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
        Mockito.when(order.verify(any(OrderEntity.class))).thenReturn(true);
        Mockito.when(walletFactory.getWallet(anyInt())).thenReturn(Optional.of(wallet));

        BetResult betResult = guest.bet(testBetRequest);

        Assertions.assertTrue(betResult.isSuccess());
        Assertions.assertNull(betResult.getErrorMessage());
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