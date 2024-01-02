package com.cooba.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletRequest {
    private int walletId;
    private int assetId;
    private BigDecimal amount;
}
