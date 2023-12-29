package com.cooba.task;

import com.cooba.component.admin.Admin;
import com.cooba.component.lottery.Lottery;
import com.cooba.object.WinningNumberInfo;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class MarkSixLotteryDrawTask extends QuartzJobBean implements Task {
    private final Lottery lottery;
    private final Admin lotterySystem;

    public MarkSixLotteryDrawTask(@Qualifier("MarkSixLottery") Lottery lottery, Admin lotterySystem) {
        this.lottery = lottery;
        this.lotterySystem = lotterySystem;
    }

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
                Thread.sleep(timeDiff * 1000L);
            } catch (InterruptedException e) {
                log.error("沉睡遇到錯誤 {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void executeInternal(JobExecutionContext context) {
        log.info("開始執行 {}", context.getJobDetail().getDescription());
        execute();
    }
}
