package com.cooba.repository.playerWallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SqlPlayerWalletRepository implements PlayerWalletRepository {
    @Override
    public Optional<BigDecimal> selectAssetAmount(long playerId, int assetId) {
        return Optional.empty();
    }

    @Override
    public void insertAssetAmount(long playerId, int assetId, BigDecimal amount) {

    }

    @Override
    public void updateAssetAmount(long playerId, int assetId, BigDecimal amount) {

    }
}
