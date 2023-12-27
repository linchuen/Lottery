package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;

import java.math.BigDecimal;

public interface Wallet {
    void depositAsset(long playerId, int assetId, BigDecimal amount);

    void withdrawAsset(long playerId, int assetId, BigDecimal amount) throws Exception;

    WalletEnum getWalletEnum();
}
