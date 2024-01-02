package com.cooba.component.player;

import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.request.WalletRequest;

public interface Player {
    CreatePlayerResult create(CreatePlayerRequest createRequest);

    BetResult bet(long playerId, BetRequest betRequest);

    void deposit(long playerId, WalletRequest walletRequest);

    void withdraw(long playerId, WalletRequest walletRequest) throws InsufficientBalanceException;
}
