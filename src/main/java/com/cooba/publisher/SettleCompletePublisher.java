package com.cooba.publisher;

import com.cooba.enums.QueueEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SettleCompletePublisher implements Publisher {
    private final AmqpTemplate rabbitTemplate;

    @Override
    public void publishEvent(String orderId) {
        rabbitTemplate.convertAndSend(QueueEnum.SETTLE_COMPLETED.name(), orderId);
    }
}
