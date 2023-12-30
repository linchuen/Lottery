package com.cooba.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class LotteryNumberEntity {
    private int lotteryId;
    private long round;
    private String numberString;
    private List<Integer> winningNumbers;
    private LocalDateTime createdTime;
}
