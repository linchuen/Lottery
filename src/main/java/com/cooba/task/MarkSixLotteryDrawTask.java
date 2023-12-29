package com.cooba.task;

import com.cooba.component.admin.Admin;
import com.cooba.component.lottery.MarkSixLottery;
import com.cooba.object.WinningNumberInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarkSixLotteryDrawTask implements Task {
    private final MarkSixLottery lottery;
    private final Admin lotterySystem;

    @Override
    public void execute() {
        LocalDateTime now = LocalDateTime.now();
        WinningNumberInfo winningNumberInfo = lottery.generateNextRoundNumbers(now).orElseThrow();
        log.info("{} 開出獎號:{} 期數:{}", lottery.getLotteryEnum(), winningNumberInfo.getWinningNumbers(), winningNumberInfo.getRound());

        LocalDateTime nextRoundTime = lottery.calculateNextRoundTime(now);
        sleepUtilNextRoundTime(nextRoundTime, now);

        log.info("{} 期數:{} 開始結算", lottery.getLotteryEnum(), winningNumberInfo.getRound());
        lotterySystem.settleOrders(winningNumberInfo);
        log.info("{} 期數:{} 結算結束", lottery.getLotteryEnum(), winningNumberInfo.getRound());
    }

    private void sleepUtilNextRoundTime(LocalDateTime nextRoundTime, LocalDateTime now) {
        ZoneOffset zoneOffset = ZoneOffset.of("+08:00");
        int timeDiff = (int) (nextRoundTime.toEpochSecond(zoneOffset) - now.toEpochSecond(zoneOffset));
        if (timeDiff > 0) {
            try {
                Thread.sleep(timeDiff);
            } catch (InterruptedException e) {
                log.error("沉睡遇到錯誤 {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public <T> T getCron() {
        return null;
    }
}
