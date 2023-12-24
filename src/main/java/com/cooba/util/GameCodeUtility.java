package com.cooba.util;

import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.object.GameInfo;
import com.cooba.object.PlayParameter;
import com.cooba.enums.ColorEnum;
import org.springframework.stereotype.Component;

@Component
public class GameCodeUtility {

    public GameInfo parse(String gameCode) {
        int lotteryId = Integer.parseInt(gameCode.substring(0, 3));
        int gameRuleId = Integer.parseInt(gameCode.substring(3, 7));
        int position = Integer.parseInt(gameCode.substring(7, 9));
        int isBig = Integer.parseInt(gameCode.substring(9, 10));
        assert isBig <= 2;
        int isOdd = Integer.parseInt(gameCode.substring(10, 11));
        assert isOdd <= 2;
        int color = Integer.parseInt(gameCode.substring(11, 12));

        return GameInfo.builder()
                .lotteryId(lotteryId)
                .gameRuleId(gameRuleId)
                .playParameter(PlayParameter.builder()
                        .position(position == 0 ? null : position)
                        .isBig(isBig == 2 ? null : isBig == 1)
                        .isOdd(isOdd == 2 ? null : isOdd == 1)
                        .color(color == 0 ? null : ColorEnum.getColorById(color))
                        .build())
                .build();
    }

    public String getLotteryCode(LotteryEnum lotteryEnum) {
        return String.format("%03d", lotteryEnum.getId());
    }

    public String getRuleCode(GameRuleEnum gameRuleEnum) {
        return String.format("%04d", gameRuleEnum.getId());
    }

    public String getPositionCode(int position) {
        return position == 0 ? null : String.format("%02d", position);
    }

    public String getIsBigCode(Boolean isBig) {
        return isBig == null ? "2" : isBig ? "1" : "0";
    }

    public String getIsOddCode(Boolean isOdd) {
        return isOdd == null ? "2" : isOdd ? "1" : "0";
    }
}
