package com.cooba.entity;

import lombok.Data;

@Data
public class OrderEntity {
    private long id;
    private long playerId;
    private String gameCode;
}
