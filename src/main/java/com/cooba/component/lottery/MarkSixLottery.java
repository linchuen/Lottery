package com.cooba.component.lottery;

import com.cooba.component.playRule.PlayRule;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LorreryRoundEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.LotteryNumberEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.repository.LotteryNumber.LotteryNumberRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
@Component
public class MarkSixLottery implements Lottery {
    private final List<PlayRule> playRules;
    private final LotteryNumberRepository lotteryNumberRepository;
    private Map<GameRuleEnum, PlayRule> playRuleMap;

    public MarkSixLottery(List<PlayRule> playRules, LotteryNumberRepository lotteryNumberRepository) {
        this.playRules = playRules;
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
    public void generateNextRoundNumbers(LorreryRoundEnum roundEnum, LotteryNumberEnum numberEnum) {
        Long nextRound = getNextRoundFromLastRound(roundEnum).orElseGet(() -> calculateNextRound(roundEnum));

    }

    private long calculateNextRound(LorreryRoundEnum roundEnum) {
        int startNum = roundEnum.getNum();
        int startTime = roundEnum.getStart().toSecondOfDay();
        int now = LocalTime.now().toSecondOfDay();
        int timeDiff = now - startTime;
        int intervalSecond = (int) roundEnum.getTimeUnit().toSeconds(roundEnum.getIntervalTime());
        int round = startNum + timeDiff / intervalSecond;
        String dateString = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nextRoundString = dateString + String.format(roundEnum.getFormat(), round);
        return Long.parseLong(nextRoundString);
    }

    private Optional<Long> getNextRoundFromLastRound(LorreryRoundEnum roundEnum) {
        Long lastRound = lotteryNumberRepository.getLastRound(getLotteryEnum().getId());
        if (lastRound == null) {
            return Optional.empty();
        }

        Pattern pattern = Pattern.compile("([0-9]{8})([0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher("20231226001");
        boolean matchFound = matcher.find();
        if (!matchFound) {
            return Optional.empty();
        }

        String dateString = matcher.group(1);
        int lastRoundNumber = Integer.parseInt(matcher.group(2));
        String nextRoundString = dateString + String.format(roundEnum.getFormat(), lastRoundNumber + 1);
        Long nextRound = Long.valueOf(nextRoundString);
        return Optional.of(nextRound);
    }


    public static void main(String[] args) {
        LocalTime localTime = LocalTime.of(0, 0, 0);
        System.out.println(localTime.plus(35, TimeUnit.HOURS.toChronoUnit()));
    }


    @Override
    public LotteryEnum getLotteryEnum() {
        return LotteryEnum.MarkSix;
    }
}
