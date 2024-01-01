package com.cooba.request;

import lombok.Data;

@Data
public class CreatePlayerRequest {
    private int walletId;
    private int assetId;
}
