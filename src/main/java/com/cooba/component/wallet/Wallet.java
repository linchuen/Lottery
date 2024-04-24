package com.cooba.component.wallet;

import com.cooba.enums.WalletEnum;
import com.cooba.object.PlayerWalletResult;

import java.math.BigDecimal;

public interface Wallet {
    PlayerWalletResult increaseAsset(long playerId, int assetId, BigDecimal amount);

    PlayerWalletResult decreaseAsset(long playerId, int assetId, BigDecimal amount);

    WalletEnum getWalletEnum();
}
