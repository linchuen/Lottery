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
import com.cooba.util.LockUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class Guest implements Player {
    private final Order order;
    private final WalletFactory walletFactory;
    private final OrderRepository orderRepository;
    private final LockUtil lockUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CreatePlayerResult create(CreatePlayerRequest createRequest) {
        Random random = new SecureRandom();
        long playerId = random.nextInt(100) + 1;

        Wallet wallet = walletFactory.getWallet(WalletEnum.SIMPLE.getId()).orElseThrow();
        int assetId = AssetEnum.TWD.getId();
        BigDecimal amount = BigDecimal.valueOf(2000);
        wallet.increaseAsset(playerId, assetId, amount);

        return CreatePlayerResult.builder()
                .playerId(playerId)
                .walletId(WalletEnum.SIMPLE.getId())
                .assetId(assetId)
                .amount(amount)
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BetResult bet(long playerId, BetRequest betRequest) {
        String key = playerId + betRequest.getGameCode();
        Supplier<BetResult> betProcess = () -> {
            OrderEntity newOrder = order.generate(betRequest);
            if (!order.valid(newOrder)) {
                return getErrorBetResult("驗證失敗", betRequest);
            }

            long orderId = orderRepository.insertNewOrder(newOrder);

            int assetId = betRequest.getAssetId();
            BigDecimal betAmount = betRequest.getBetAmount();

            int walletId = betRequest.getWalletId();
            Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();

            BetResult betResult = new BetResult();
            betResult.setOrderId(orderId);
            betResult.addBetRequestAttribute(betRequest);
            try {
                wallet.decreaseAsset(playerId, assetId, betAmount);

                orderRepository.updatePayOrder(orderId);

                betResult.setSuccess(true);
                return betResult;
            } catch (Exception e) {
                orderRepository.updateCancelOrder(orderId);

                betResult.setSuccess(false);
                betResult.setErrorMessage(e.getMessage());
                return betResult;
            }
        };
        return lockUtil.tryLock(key, 1, TimeUnit.SECONDS, 3, betProcess)
                .orElseGet(() -> getErrorBetResult("無法取得鎖", betRequest));
    }

    private static BetResult getErrorBetResult(String msg, BetRequest betRequest) {
        BetResult betResult = new BetResult();
        betResult.setSuccess(false);
        betResult.setErrorMessage(msg);
        betResult.addBetRequestAttribute(betRequest);
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
