package com.cooba.repository.lotteryNumber;

import com.cooba.mapper.LotteryNumberMapper;
import com.cooba.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SqlLotteryNumberRepository implements LotteryNumberRepository {
    private final LotteryNumberMapper lotteryNumberMapper;

    @Override
    public boolean insertNumber(int lotteryId, long round, List<Integer> winningNumbers) {
        int result = lotteryNumberMapper.insertNumbers(lotteryId, round, JsonUtil.toJsonString(winningNumbers));
        return result == 1;
    }
}
