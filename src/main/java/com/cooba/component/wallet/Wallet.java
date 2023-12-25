package com.cooba.component.wallet;

import java.math.BigDecimal;

public interface Wallet {
    void increaseAsset(long playerId, int assetId, BigDecimal amount);
}
