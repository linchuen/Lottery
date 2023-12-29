package com.cooba.repository;

import com.cooba.repository.LotteryNumber.LotteryNumberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FakeLotteryNumberRepository implements LotteryNumberRepository {
    private final Map<Key, List<Integer>> winningNumberMap = new HashMap<>();

    public Optional<List<Integer>> selectNumber(int lotteryId, long round) {
        return Optional.ofNullable(winningNumberMap.get(new Key(lotteryId, round)));
    }


    @Override
    public boolean insertNumber(int lotteryId, long round, List<Integer> winningNumbers) {
        if (selectNumber(lotteryId, round).isPresent()) return false;

        winningNumberMap.put(new Key(lotteryId, round), winningNumbers);
        return true;
    }

    @Data
    @AllArgsConstructor
    private static class Key {
        private int lotteryId;
        private long round;
    }
}
