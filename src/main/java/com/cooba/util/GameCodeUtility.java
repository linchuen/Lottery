package com.cooba.util;

import com.cooba.enums.ColorEnum;
import com.cooba.enums.GameRuleEnum;
import com.cooba.enums.LotteryEnum;
import com.cooba.object.GameInfo;
import com.cooba.object.PlayParameter;
import org.springframework.stereotype.Component;

/**
 * "000" + "0000" + "00" + "2" + "2" + "00"
 *  彩種     玩法     位置   大小   奇偶   顏色
 */
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
                        .isBig(isBig == 0 ? null : isBig == 1)
                        .isOdd(isOdd == 0 ? null : isOdd == 1)
                        .color(color == 0 ? null : ColorEnum.getColorById(color))
                        .build())
                .build();
    }

    public String getLotteryCode(LotteryEnum lotteryEnum) {
        return lotteryEnum == null ? "000" : String.format("%03d", lotteryEnum.getId());
    }

    public String getRuleCode(GameRuleEnum gameRuleEnum) {
        return gameRuleEnum == null ? "0000" : String.format("%04d", gameRuleEnum.getId());
    }

    public String getPositionCode(Integer position) {
        return position == null ? "00" : String.format("%02d", position);
    }

    public String getIsBigCode(Boolean isBig) {
        return isBig == null ? "0" : isBig ? "1" : "2";
    }

    public String getIsOddCode(Boolean isOdd) {
        return isOdd == null ? "0" : isOdd ? "1" : "2";
    }

    public String getColorCode(ColorEnum colorEnum) {
        return colorEnum == null ? "00" : String.format("%02d", colorEnum.getId());
    }

    public String generate(LotteryEnum lotteryEnum, GameRuleEnum gameRuleEnum, Integer position, Boolean isBig, Boolean isOdd, ColorEnum colorEnum) {
        return getLotteryCode(lotteryEnum) +
                getRuleCode(gameRuleEnum) +
                getPositionCode(position) +
                getIsBigCode(isBig) +
                getIsOddCode(isOdd) +
                getColorCode(colorEnum);
    }

    public int getLotteryId(String gameCode) {
        return Integer.parseInt(gameCode.substring(0, 3));
    }
}
