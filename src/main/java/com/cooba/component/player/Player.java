package com.cooba.component.player;

import com.cooba.exception.InsufficientBalanceException;
import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.object.PlayerWalletResult;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;
import com.cooba.request.WalletRequest;

public interface Player {
    CreatePlayerResult create(CreatePlayerRequest createRequest);

    BetResult bet(BetRequest betRequest);

    PlayerWalletResult deposit(long playerId, WalletRequest walletRequest);

    PlayerWalletResult withdraw(long playerId, WalletRequest walletRequest) throws InsufficientBalanceException;
}
