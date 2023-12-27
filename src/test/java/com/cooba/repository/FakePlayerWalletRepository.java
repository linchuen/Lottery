package com.cooba.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class FakePlayerWalletRepository implements PlayerWalletRepository {
    private final Map<Key, BigDecimal> walletMap = new HashMap<>();

    @Override
    public Optional<BigDecimal> selectAssetAmount(long playerId, int assetId) {
        return Optional.ofNullable(walletMap.get(new Key(playerId, assetId)));
    }

    @Override
    public void insertAssetAmount(long playerId, int assetId, BigDecimal amount) {
        walletMap.put(new Key(playerId, assetId), amount);
    }

    @Override
    public void updateAssetAmount(long playerId, int assetId, BigDecimal amount) {
        walletMap.put(new Key(playerId, assetId), amount);
    }

    @Data
    @AllArgsConstructor
    private static class Key {
        private long playerId;
        private int assetId;
    }
}
