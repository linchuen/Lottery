package com.cooba.component.player;

import com.cooba.object.BetResult;
import com.cooba.request.BetRequest;

public interface Player {
    BetResult bet(long playerId, BetRequest betRequest);
}
