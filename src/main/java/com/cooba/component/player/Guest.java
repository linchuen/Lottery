package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.object.BetResult;
import com.cooba.repository.OrderRepository;
import com.cooba.request.BetRequest;
import com.cooba.util.LockUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
    public BetResult bet(long playerId, BetRequest betRequest) {
        String key = playerId + betRequest.getGameCode();
        Supplier<BetResult> betProcess = () -> {
            OrderEntity newOrder = order.generate(betRequest);
            if (!order.valid(newOrder)) {
                return getErrorBetResult("驗證失敗", betRequest);
            }

            long orderId = orderRepository.insertNewOrder(newOrder);

            int walletId = betRequest.getWalletId();
            int assetId = betRequest.getAssetId();
            BigDecimal betAmount = betRequest.getBetAmount();

            Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();
            try {
                wallet.decreaseAsset(playerId, assetId, betAmount);
                orderRepository.updatePayOrder(orderId);

                BetResult betResult = new BetResult();
                betResult.setOrderId(orderId);
                betResult.setSuccess(true);
                betResult.addBetRequestAttribute(betRequest);
                return betResult;
            } catch (Exception e) {
                orderRepository.updateCancelOrder(orderId);

                BetResult betResult = getErrorBetResult(e.getMessage(), betRequest);
                betResult.setOrderId(orderId);
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

}
