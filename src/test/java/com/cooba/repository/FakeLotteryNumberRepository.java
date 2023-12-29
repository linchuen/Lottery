package com.cooba.repository;

import com.cooba.repository.LotteryNumber.LotteryNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FakeLotteryNumberRepository implements LotteryNumberRepository {

    @Override
    public boolean insertNumber(int lotteryId, long round, List<Integer> winningNumbers) {
        return false;
    }
}
