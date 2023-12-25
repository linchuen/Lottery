package com.cooba.repository;

public interface LotteryNumberRepository {
    Long getNextRound(int lotteryId);
}
