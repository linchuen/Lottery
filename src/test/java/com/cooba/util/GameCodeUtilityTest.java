package com.cooba.util;

import com.cooba.object.GameInfo;
import com.cooba.object.PlayParameter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameCodeUtilityTest {
    @Test
    void isLose() {
        GameCodeUtility gameCodeUtility = new GameCodeUtility();
        GameInfo gameInfo = gameCodeUtility.parse("000" + "0000" + "00" + "2" + "2" + "00");

        Assertions.assertEquals(gameInfo.getLotteryId(), 0);
        Assertions.assertEquals(gameInfo.getGameRuleId(), 0);

        PlayParameter playParameter = gameInfo.getPlayParameter();
        Assertions.assertNull(playParameter.getPosition());
        Assertions.assertNull(playParameter.getIsBig());
        Assertions.assertNull(playParameter.getIsOdd());
        Assertions.assertNull(playParameter.getColor());
    }
}