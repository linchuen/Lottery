package com.cooba.object;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class SettleResult {
    private BigDecimal fee;
    private BigDecimal betPrize;
    private int status;
    private Integer gameStatus;
}
