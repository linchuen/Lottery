package com.cooba.repository.LotteryNumber;

public interface LotteryNumberRepository {
    Long getNextRound(int lotteryId);
}
