package com.cooba.task;

import com.cooba.component.admin.Admin;
import com.cooba.component.lottery.Lottery;
import com.cooba.object.WinningNumberInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class LotteryDrawTaskTest {
    @InjectMocks
    MarkSixLotteryDrawTask markSixLotteryDrawTask;
    @Mock
    Lottery lottery;
    @Mock
    Admin admin;

    @Test
    void waitForExecute() {
        Mockito.when(lottery.generateNextRoundNumbers(any())).thenReturn(Optional.of(WinningNumberInfo.builder().build()));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime testNextRoundTime = now.plusSeconds(2);
        Mockito.when(lottery.calculateNextRoundTime(any())).thenReturn(testNextRoundTime);

        long start = System.currentTimeMillis();
        markSixLotteryDrawTask.execute();
        long end = System.currentTimeMillis();

        long elapseTime = end - start;
        Assertions.assertTrue(elapseTime > TimeUnit.SECONDS.toMillis(2L));
    }

    @Test
    void directlyExecute() {
        Mockito.when(lottery.generateNextRoundNumbers(any())).thenReturn(Optional.of(WinningNumberInfo.builder().build()));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime testNextRoundTime = now.minusSeconds(2);
        Mockito.when(lottery.calculateNextRoundTime(any())).thenReturn(testNextRoundTime);

        long start = System.currentTimeMillis();
        markSixLotteryDrawTask.execute();
        long end = System.currentTimeMillis();

        long elapseTime = end - start;
        System.out.println(elapseTime);
        Assertions.assertTrue(elapseTime < TimeUnit.SECONDS.toMillis(1L));
    }
}