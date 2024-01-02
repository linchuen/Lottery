package com.cooba.subscriber;

import com.cooba.component.admin.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "SETTLE_COMPLETED")
@RequiredArgsConstructor
public class SettleCompleteSubscriber implements Subscriber {
    private final Admin admin;

    @Override
    @RabbitHandler
    public void handleEvent(String orderId) {
        admin.sendLotteryPrize(Long.parseLong(orderId));
    }
}
