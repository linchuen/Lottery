package com.cooba.util;

import com.cooba.object.PlayParameter;
import com.cooba.enums.ColorEnum;

public class GameCodeParser {

    public PlayParameter parse(String gameCode) {
        int lotteryId = Integer.parseInt(gameCode.substring(0, 3));
        int gameRuleId = Integer.parseInt(gameCode.substring(3, 7));
        int position = Integer.parseInt(gameCode.substring(7, 9));
        int isBig = Integer.parseInt(gameCode.substring(9, 10));
        int isOdd = Integer.parseInt(gameCode.substring(10, 11));
        int color = Integer.parseInt(gameCode.substring(11, 12));

        return PlayParameter.builder()
                .position(position == 0 ? null : position)
                .isBig(isBig == 0 ? null : isBig == 1)
                .isOdd(isOdd == 0 ? null : isOdd == 1)
                .color(color == 0 ? null : ColorEnum.getColorById(color))
                .build();
    }
}
