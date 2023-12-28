package com.cooba.repository.LotteryNumber;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SqlLotteryNumberRepository implements LotteryNumberRepository {
    @Override
    public Long getNextRound(int lotteryId) {
        return null;
    }
}
