package com.cooba.object;

import com.cooba.request.BetRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BetResult {
    private Long orderId;
    private boolean isSuccess;
    private String errorMessage;

    private long playerId;
    private int walletId;
    private int assetId;
    private long round;
    private String gameCode;
    private List<Integer> guessNumbers;
    private BigDecimal betAmount;

    public void addBetRequestAttribute(BetRequest betRequest) {
        this.playerId = betRequest.getPlayerId();
        this.walletId = betRequest.getWalletId();
        this.assetId = betRequest.getAssetId();
        this.round = betRequest.getRound();
        this.gameCode = betRequest.getGameCode();
        this.guessNumbers = betRequest.getGuessNumbers();
        this.betAmount = betRequest.getBetAmount();
    }
}
