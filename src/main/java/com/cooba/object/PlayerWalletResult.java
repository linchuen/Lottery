package com.cooba.object;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PlayerWalletResult {
    private long playerId;
    private int walletId;
    private int assetId;
    private BigDecimal amount;
}
