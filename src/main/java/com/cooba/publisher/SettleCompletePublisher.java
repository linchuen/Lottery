package com.cooba.publisher;

import org.springframework.stereotype.Component;

@Component
public class SettleCompletePublisher implements Publisher{
    @Override
    public <T> void publishEvent(T t) {

    }
}
