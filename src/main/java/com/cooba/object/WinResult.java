package com.cooba.object;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class WinResult extends PlayResult {
    private final boolean isWin = true;

    public WinResult() {
    }

    public WinResult(BigDecimal betOdds) {
        super(betOdds);
    }
}
