package com.cooba.object;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PlayResult {
    private boolean isWin;
    private BigDecimal betOdds;
    private boolean isTie;

    public PlayResult() {
        this.betOdds = BigDecimal.ZERO;
    }

    public PlayResult(BigDecimal betOdds) {
        this.betOdds = betOdds;
    }
}
