package com.cooba.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class SimpleWalletEntity extends WalletEntity{
    private long playerId;
    private int assetId;
    private BigDecimal amount;
    private LocalDateTime updatedTime;
}
