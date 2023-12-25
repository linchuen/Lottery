package com.cooba.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FakeLotteryNumberRepository implements LotteryNumberRepository{
    @Override
    public Long getNextRound(int lotteryId) {
        return null;
    }
}
