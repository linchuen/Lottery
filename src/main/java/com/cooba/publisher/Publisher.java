package com.cooba.publisher;

public interface Publisher {
    <T> void publishEvent(T t);
}
