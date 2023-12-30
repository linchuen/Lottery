package com.cooba.component.lottery;

import com.cooba.component.numberGenerator.NumberGenerator;
import com.cooba.component.playRule.PlayRule;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LorreryRoundEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.LotteryNumberEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinningNumberInfo;
import com.cooba.repository.lotteryNumber.LotteryNumberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 玩法
 * 中n
 * 猜第n球
 * 第n球 大
 * 第n球 小
 * 第n球 單
 * 第n球 雙
 * 總和 大
 * 總和 小
 * 總和 單
 * 總和 雙
 * 自選n不中
 * 色波
 */
@Slf4j
@Component
public class MarkSixLottery implements Lottery {
    private final NumberGenerator numberGenerator;
    private final LotteryNumberRepository lotteryNumberRepository;
    private Map<GameRuleEnum, PlayRule> playRuleMap;

    public MarkSixLottery(List<PlayRule> playRules, NumberGenerator numberGenerator, LotteryNumberRepository lotteryNumberRepository) {
        this.numberGenerator = numberGenerator;
        this.lotteryNumberRepository = lotteryNumberRepository;

        init(playRules);
    }

    private void init(List<PlayRule> playRules) {
        this.playRuleMap = playRules.stream()
                .collect(Collectors.toMap(PlayRule::getGameRuleEnum, Function.identity()));
    }

    @Override
    public PlayResult checkNumbers(GameRuleEnum ruleEnum, List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        PlayRule playRule = playRuleMap.get(ruleEnum);
        return playRule.decideResult(winningNumbers, guessNumbers, playParameter);
    }

    @Override
    public Optional<WinningNumberInfo> generateNextRoundNumbers(LocalDateTime time) {
        long nextRound = calculateNextRound(time);

        LotteryNumberEnum numberEnum = this.getLotteryNumberEnum();
        List<Integer> winningNumbers = numberGenerator.generate(numberEnum);

        int lotteryId = getLotteryEnum().getId();
        boolean isSuccess = lotteryNumberRepository.insertNumber(lotteryId, nextRound, winningNumbers);
        if (!isSuccess) {
            log.info("{} 期數:{} 無法開出號碼 時間:{}", getLotteryEnum(), nextRound, time);
            return Optional.empty();
        }
        return Optional.of(WinningNumberInfo.builder()
                .lotteryId(lotteryId)
                .round(nextRound)
                .winningNumbers(winningNumbers)
                .build());
    }

    @Override
    public long calculateNextRound(LocalDateTime now) {
        LorreryRoundEnum roundEnum = this.getLorreryRoundEnum();
        int startNum = roundEnum.getStartRound();
        int intervalSecond = (int) roundEnum.getTimeUnit().toSeconds(roundEnum.getIntervalTime());
        //開始時間已是第2期
        int startTimeSecond = roundEnum.getStart().toSecondOfDay() - intervalSecond;

        int nowSecond = now.toLocalTime().toSecondOfDay();
        int timeDiff = nowSecond - startTimeSecond;
        DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
        if (timeDiff < (int) TimeUnit.DAYS.toSeconds(1)) {
            int nextRound = startNum + timeDiff / intervalSecond;
            String dateString = now.format(yyyyMMdd);
            String nextRoundString = dateString + String.format(roundEnum.getFormat(), nextRound);
            return Long.parseLong(nextRoundString);
        } else {
            String dateString = now.plusDays(1).format(yyyyMMdd);
            String nextRoundString = dateString + String.format(roundEnum.getFormat(), startNum);
            return Long.parseLong(nextRoundString);
        }
    }

    @Override
    public LocalDateTime calculateNextRoundTime(LocalDateTime now) {
        LorreryRoundEnum roundEnum = this.getLorreryRoundEnum();
        int intervalSecond = (int) roundEnum.getTimeUnit().toSeconds(roundEnum.getIntervalTime());
        int startTimeSecond = roundEnum.getStart().toSecondOfDay();

        int nowSecond = now.toLocalTime().toSecondOfDay();
        int roundAfterSeconds = (nowSecond - startTimeSecond) % intervalSecond;
        return now.plusSeconds(intervalSecond - roundAfterSeconds);
    }


    @Override
    public LotteryEnum getLotteryEnum() {
        return LotteryEnum.MarkSix;
    }

    @Override
    public LotteryNumberEnum getLotteryNumberEnum() {
        return LotteryNumberEnum.MarkSix;
    }

    @Override
    public LorreryRoundEnum getLorreryRoundEnum() {
        return LorreryRoundEnum.MarkSix;
    }
}
