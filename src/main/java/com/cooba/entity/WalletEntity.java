package com.cooba.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class WalletEntity {
    private long playerId;
    private int assetId;
    private BigDecimal amount;
    private LocalDateTime updatedTime;

}
