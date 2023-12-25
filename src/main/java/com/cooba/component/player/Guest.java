package com.cooba.component.player;

import com.cooba.component.order.Order;
import com.cooba.component.wallet.Wallet;
import com.cooba.component.wallet.WalletFactory;
import com.cooba.entity.OrderEntity;
import com.cooba.object.BetResult;
import com.cooba.repository.OrderRepository;
import com.cooba.request.BetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class Guest implements Player {
    private final Order order;
    private final WalletFactory walletFactory;
    private final OrderRepository orderRepository;

    @Override
    public BetResult bet(long playerId, BetRequest betRequest) {
        OrderEntity newOrder = order.generate(betRequest);
        if (!order.valid(newOrder)) {
            BetResult betResult = new BetResult();
            return betResult;
        }

        long orderId = orderRepository.insertNewOrder(newOrder);

        Wallet wallet = walletFactory.getWallet(1).orElseThrow();
        int assetId = betRequest.getAssetId();
        BigDecimal betAmount = betRequest.getBetAmount();
        try {
            wallet.decreaseAsset(playerId, assetId, betAmount);
        } catch (Exception e) {
            orderRepository.updatePayOrder(orderId);
        }
        BetResult betResult = new BetResult();
        return betResult;
    }
}
