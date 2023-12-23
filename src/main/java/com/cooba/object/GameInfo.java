package com.cooba.object;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GameInfo {
    private int lotteryId;
    private int gameRuleId;
    private PlayParameter playParameter;
}
