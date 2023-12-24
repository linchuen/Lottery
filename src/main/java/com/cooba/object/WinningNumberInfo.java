package com.cooba.object;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Builder
@Getter
public class WinningNumberInfo {
    private int lotteryId;
    private long round;
    private List<Integer> winningNumbers;
}
