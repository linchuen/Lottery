package com.cooba.repository.PlayerWallet;

import java.math.BigDecimal;
import java.util.Optional;

public interface PlayerWalletRepository {
    Optional<BigDecimal> selectAssetAmount(long playerId, int assetId);

    void insertAssetAmount(long playerId, int assetId, BigDecimal amount);

    void updateAssetAmount(long playerId, int assetId, BigDecimal amount);
}
