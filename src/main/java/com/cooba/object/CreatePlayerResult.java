package com.cooba.object;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
public class CreatePlayerResult {
    private long playerId;
    private int walletId;
    private int assetId;
    private BigDecimal amount;
}
