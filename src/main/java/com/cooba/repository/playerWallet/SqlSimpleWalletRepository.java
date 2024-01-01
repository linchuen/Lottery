package com.cooba.repository.playerWallet;

import com.cooba.entity.SimpleWalletEntity;
import com.cooba.mapper.SimpleWalletMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SqlSimpleWalletRepository implements PlayerWalletRepository {
    private final SimpleWalletMapper simpleWalletMapper;

    @Override
    public Optional<BigDecimal> selectAssetAmount(long playerId, int assetId) {
        return simpleWalletMapper.selectPlayerAsset(playerId, assetId).map(SimpleWalletEntity::getAmount);
    }

    @Override
    public void insertAssetAmount(long playerId, int assetId, BigDecimal amount) {
        simpleWalletMapper.insertAsset(playerId, assetId, amount);
    }

    @Override
    public void updateAssetAmount(long playerId, int assetId, BigDecimal amount) {
        simpleWalletMapper.updateAsset(playerId, assetId, amount);
    }
}
