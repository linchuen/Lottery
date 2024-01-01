package com.cooba.component.player;

import com.cooba.object.BetResult;
import com.cooba.object.CreatePlayerResult;
import com.cooba.request.BetRequest;
import com.cooba.request.CreatePlayerRequest;

public interface Player {
    CreatePlayerResult create(CreatePlayerRequest createRequest);
    BetResult bet(long playerId, BetRequest betRequest);
}
