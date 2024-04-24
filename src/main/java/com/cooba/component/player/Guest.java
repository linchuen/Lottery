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
import com.cooba.object.PlayerWalletResult;
import com.cooba.repository.order.OrderRepository;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.request.WalletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class Guest implements Player {
    private final Order order;
    private final WalletFactory walletFactory;
    private final OrderRepository orderRepository;
    private final RedisTemplate<String,String> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreatePlayerResult create(CreatePlayerRequest createRequest) {
        Random random = new SecureRandom();
        long playerId = random.nextInt(100) + 1;

        Wallet wallet = walletFactory.getWallet(WalletEnum.INNER.getId()).orElseThrow();
        int assetId = AssetEnum.TWD.getId();
        BigDecimal amount = BigDecimal.valueOf(2000);
        wallet.increaseAsset(playerId, assetId, amount);

        return CreatePlayerResult.builder()
                .playerId(playerId)
                .walletId(WalletEnum.INNER.getId())
                .assetId(assetId)
                .amount(amount)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BetResult bet(BetRequest betRequest) {
        long playerId = betRequest.getPlayerId();

        OrderEntity newOrder = order.generate(betRequest);
        if (!order.verify(newOrder)) {
            throw new RuntimeException("驗證失敗");
        }
        long orderId = orderRepository.insertNewOrder(newOrder);

        int assetId = betRequest.getAssetId();
        BigDecimal betAmount = betRequest.getBetAmount();
        int walletId = betRequest.getWalletId();
        Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();
        wallet.decreaseAsset(playerId, assetId, betAmount);

        orderRepository.updatePayOrder(orderId);

        BetResult betResult = new BetResult();
        betResult.setOrderId(orderId);
        betResult.addBetRequestAttribute(betRequest);
        betResult.setSuccess(true);
        return betResult;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlayerWalletResult deposit(long playerId, WalletRequest walletRequest) {
        int walletId = walletRequest.getWalletId();
        int assetId = walletRequest.getAssetId();
        BigDecimal amount = walletRequest.getAmount();

        Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();
        return wallet.increaseAsset(playerId, assetId, amount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PlayerWalletResult withdraw(long playerId, WalletRequest walletRequest) throws InsufficientBalanceException {
        int walletId = walletRequest.getWalletId();
        int assetId = walletRequest.getAssetId();
        BigDecimal amount = walletRequest.getAmount();

        Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();
        return wallet.decreaseAsset(playerId, assetId, amount);
    }
}
