package com.cooba.component.lottery;

import com.cooba.enums.LotteryEnum;

import java.util.Optional;

public interface LotteryFactory {
    Lottery getLottery(LotteryEnum lotteryEnum);
    Optional<Lottery> getLottery(int id);
}
