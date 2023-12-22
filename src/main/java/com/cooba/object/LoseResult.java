package com.cooba.object;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoseResult extends PlayResult {
    private static LoseResult instance;

    private final boolean isWin = false;
    private final BigDecimal bettingOdds = BigDecimal.ZERO;

    public static LoseResult getInstance() {
        if (instance != null) {
            return instance;
        }
        return new LoseResult();
    }
}
