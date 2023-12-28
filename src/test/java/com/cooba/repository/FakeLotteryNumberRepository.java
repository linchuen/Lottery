package com.cooba.repository;

import com.cooba.repository.LotteryNumber.LotteryNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FakeLotteryNumberRepository implements LotteryNumberRepository {
    @Override
    public Long getNextRound(int lotteryId) {
        return null;
    }
}
