package com.cooba.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderEntity {
    private long id;
    private long playerId;
    private int walletId;
    private int assetId;
    private int lotteryId;
    private long round;
    private String gameCode;
    private String guessString;
    private List<Integer> guessNumbers;
    private BigDecimal betAmount;

    private BigDecimal odds;
    private BigDecimal betPrize;
    private BigDecimal fee;
    private int status;
    private Integer gameStatus;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}