package com.cooba.mapper;

import com.cooba.entity.SimpleWalletEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface SimpleWalletMapper {
    Optional<SimpleWalletEntity> selectPlayerAsset(long playerId, int assetId);

    void insertAsset(@Param("playerId") long playerId, @Param("assetId") int assetId, @Param("amount") BigDecimal amount);

    void updateAsset(@Param("playerId") long playerId, @Param("assetId") int assetId, @Param("amount") BigDecimal amount);
}
