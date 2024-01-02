package com.cooba.config;

import com.cooba.enums.QueueEnum;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue Queue() {
        return new Queue(QueueEnum.SETTLE_COMPLETED.name());
    }
}
