package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SimpleWallet implements Wallet{
    @Override
    public void increaseAsset(long playerId, int assetId, BigDecimal amount) {

    }

    @Override
    public void decreaseAsset(long playerId, int assetId, BigDecimal amount) {

    }

    @Override
    public WalletEnum getWalletEnum() {
        return WalletEnum.SIMPLE;
    }
}
