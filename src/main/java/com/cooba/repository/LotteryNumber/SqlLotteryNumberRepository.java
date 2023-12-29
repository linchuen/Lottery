package com.cooba.repository.LotteryNumber;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SqlLotteryNumberRepository implements LotteryNumberRepository {

    @Override
    public boolean insertNumber(int lotteryId, long round, List<Integer> winningNumbers) {
        return true;
    }
}
