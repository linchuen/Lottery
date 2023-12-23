package com.cooba.component.Lottery;

import com.cooba.enums.LotteryEnum;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Component
public class LotteryFactory {
    private final Map<Integer, Lottery> lotteryIdMap;
    private final Map<LotteryEnum, Lottery> lotteryMap;

    public LotteryFactory(List<Lottery> lotteries) {
        this.lotteryMap = lotteries.stream()
                .collect(Collectors.toMap(Lottery::getLotteryEnum, Function.identity()));
        this.lotteryIdMap = lotteries.stream()
                .collect(Collectors.toMap(lottery -> lottery.getLotteryEnum().getId(), Function.identity()));
    }

    public Optional<Lottery> getLottery(int id){
        return Optional.ofNullable(lotteryIdMap.get(id));
    };
}
