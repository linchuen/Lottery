package com.cooba.task;

import com.cooba.component.admin.Admin;
import com.cooba.component.lottery.Lottery;
import com.cooba.entity.OrderEntity;
import com.cooba.enums.LotteryEnum;
import com.cooba.enums.OrderStatusEnum;
import com.cooba.object.WinningNumberInfo;
import com.cooba.repository.order.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
public class MarkSixLotteryDrawTask extends QuartzJobBean implements Task {
    private final Lottery lottery;
    private final Admin lotterySystem;
    private final OrderRepository orderRepository;

    public MarkSixLotteryDrawTask(@Qualifier("markSixLottery") Lottery lottery, Admin lotterySystem, OrderRepository orderRepository) {
        this.lottery = lottery;
        this.lotterySystem = lotterySystem;
        this.orderRepository = orderRepository;
    }

    @Override
    public void execute() {
        LocalDateTime now = LocalDateTime.now();
        WinningNumberInfo winningNumberInfo = lottery.generateNextRoundNumbers(now).orElseThrow();
        int lotteryId = winningNumberInfo.getLotteryId();
        long round = winningNumberInfo.getRound();
        LotteryEnum lotteryEnum = lottery.getLotteryEnum();
        log.info("{} 開出獎號:{} 期數:{}", lotteryEnum, winningNumberInfo.getWinningNumbers(), round);

        LocalDateTime nextRoundTime = lottery.calculateNextRoundTime(now);
        sleepUtilNextRoundTime(nextRoundTime, now);

        log.info("{} 期數:{} 開始結算", lotteryEnum, round);
        lotterySystem.settleOrders(winningNumberInfo);
        log.info("{} 期數:{} 結算結束", lotteryEnum, round);

        log.info("{} 期數:{} 開始派發獎勵", lotteryEnum, round);
        List<OrderEntity> settledOrders = orderRepository.selectOrderByStatus(lotteryId, round, OrderStatusEnum.settle);
        for (OrderEntity settledOrder : settledOrders) {
            lotterySystem.sendLotteryPrize(settledOrder);
        }
        log.info("{} 期數:{} 派發獎勵結束", lotteryEnum, round);
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
        log.info("開始執行 {}", context.getJobDetail().getKey().getName());
        execute();
    }
}
