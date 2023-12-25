package com.cooba.component.lottery;

import com.cooba.enums.LotteryEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LotteryFactoryImpl implements LotteryFactory{
    private final Map<Integer, Lottery> lotteryIdMap;
    private final Map<LotteryEnum, Lottery> lotteryMap;

    public LotteryFactoryImpl(List<Lottery> lotteries) {
        this.lotteryMap = lotteries.stream()
                .collect(Collectors.toMap(Lottery::getLotteryEnum, Function.identity()));
        this.lotteryIdMap = lotteries.stream()
                .collect(Collectors.toMap(lottery -> lottery.getLotteryEnum().getId(), Function.identity()));
    }

    @Override
    public Lottery getLottery(LotteryEnum lotteryEnum) {
        return lotteryMap.get(lotteryEnum);
    }

    @Override
    public Optional<Lottery> getLottery(int id){
        return Optional.ofNullable(lotteryIdMap.get(id));
    }
}
