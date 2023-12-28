package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.object.BetResult;
import com.cooba.repository.OrderRepository;
import com.cooba.request.BetRequest;
import com.cooba.util.LockUtil;
import lombok.Builder;
import lombok.Getter;
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
            Wallet wallet = walletFactory.getWallet(walletId).orElseThrow();
            PayResult payResult = payOrder(betRequest, playerId, wallet, orderId);

            BetResult betResult = new BetResult();
            betResult.setOrderId(orderId);
            betResult.setSuccess(payResult.isSuccess);
            betResult.setErrorMessage(payResult.errorMessage);
            betResult.addBetRequestAttribute(betRequest);
            return betResult;
        };
        return lockUtil.tryLock(key, 1, TimeUnit.SECONDS, 3, betProcess)
                .orElseGet(() -> getErrorBetResult("無法取得鎖", betRequest));
    }

    private PayResult payOrder(BetRequest betRequest, long playerId, Wallet wallet, long orderId) {
        int assetId = betRequest.getAssetId();
        BigDecimal betAmount = betRequest.getBetAmount();

        PayResult.PayResultBuilder payResultBuilder = PayResult.builder();
        try {
            wallet.decreaseAsset(playerId, assetId, betAmount);
            orderRepository.updatePayOrder(orderId);
            payResultBuilder.isSuccess(true);
        } catch (Exception e) {
            orderRepository.updateCancelOrder(orderId);
            payResultBuilder.isSuccess(false);
            payResultBuilder.errorMessage(e.getMessage());
        }
        return payResultBuilder.build();
    }

    private static BetResult getErrorBetResult(String msg, BetRequest betRequest) {
        BetResult betResult = new BetResult();
        betResult.setSuccess(false);
        betResult.setErrorMessage(msg);
        betResult.addBetRequestAttribute(betRequest);
        return betResult;
    }

    @Builder
    @Getter
    private static class PayResult {
        private boolean isSuccess;
        private String errorMessage;
    }
}
