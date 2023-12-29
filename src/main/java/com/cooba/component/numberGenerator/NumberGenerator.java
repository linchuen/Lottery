package com.cooba.component.numberGenerator;

import com.cooba.enums.LotteryNumberEnum;

import java.util.List;

public interface NumberGenerator {
    List<Integer> generate(LotteryNumberEnum numberEnum);

    List<Integer> generate(int min, int max, int length, boolean isRepeat);
}
