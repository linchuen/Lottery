package com.cooba.mapper;

import com.cooba.entity.SimpleWalletEntity;

import java.util.Optional;

public interface SimpleWalletMapper {
    Optional<SimpleWalletEntity> selectPlayerAsset(long playerId, int assetId);

    void insertAsset(SimpleWalletEntity walletEntity);

    void updateAsset(SimpleWalletEntity walletEntity);
}
