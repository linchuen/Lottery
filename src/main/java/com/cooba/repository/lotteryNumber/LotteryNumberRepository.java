package com.cooba.repository.lotteryNumber;

import java.util.List;

public interface LotteryNumberRepository {

    boolean insertNumber(int lotteryId, long round, List<Integer> winningNumbers);
}
