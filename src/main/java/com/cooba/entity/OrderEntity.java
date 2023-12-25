package com.cooba.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderEntity {
    private long id;
    private long playerId;
    private int walletId;
    private int assetId;
    private String gameCode;
    private List<Integer> guessNumbers;

    private BigDecimal betAmount;
    private BigDecimal odds;
    private BigDecimal betPrize;

    private int status;
    private Integer gameStatus;
}
