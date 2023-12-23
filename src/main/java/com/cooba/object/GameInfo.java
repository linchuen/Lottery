package com.cooba.object;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameInfo {
    private int lotteryId;
    private int gameRuleId;
    private PlayParameter playParameter;
}
