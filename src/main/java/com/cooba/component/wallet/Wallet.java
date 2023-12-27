package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import com.cooba.exception.InsufficientBalanceException;

import java.math.BigDecimal;

public interface Wallet {
    void increaseAsset(long playerId, int assetId, BigDecimal amount);

    void decreaseAsset(long playerId, int assetId, BigDecimal amount) throws InsufficientBalanceException;

    WalletEnum getWalletEnum();
}
