package com.cooba.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BetRequest {
    private String orderNo;
    private long playerId;
    private int walletId;
    private int assetId;
    private long round;
    private String gameCode;
    private List<Integer> guessNumbers;
    private BigDecimal betAmount;
}
