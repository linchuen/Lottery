package com.cooba.component.system;

import com.cooba.component.config.LotteryScan;
import com.cooba.component.config.PlayRuleScan;
import com.cooba.entity.OrderEntity;
import com.cooba.util.GameCodeUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {LotterySystem.class, GameCodeUtility.class, PlayRuleScan.class, LotteryScan.class})
class LotterySystemTest {
    @Autowired
    LotterySystem lotterySystem;

    @Test
    void lotteryNotExist() {
        OrderEntity testOrder = new OrderEntity();
        testOrder.setGameCode("000" + "0000" + "00" + "2" + "2" + "00");
        Assertions.assertThrows(NoSuchElementException.class, () -> lotterySystem.settleOrder(testOrder));
    }
}