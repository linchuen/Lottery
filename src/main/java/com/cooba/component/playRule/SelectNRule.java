package com.cooba.component.playRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;
import com.cooba.util.CombinationUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class SelectNRule implements PlayRule {
    private final BigDecimal select2Odds = BigDecimal.valueOf(CombinationUtil.c(45, 2))
            .divide(BigDecimal.valueOf(CombinationUtil.c(6, 2)), 2, RoundingMode.DOWN);// C(45,2) / C(6,2)
    private final BigDecimal select3Odds = BigDecimal.valueOf(CombinationUtil.c(45, 3))
            .divide(BigDecimal.valueOf(CombinationUtil.c(6, 3)), 2, RoundingMode.DOWN);// C(45,3) / C(6,3)
    private final BigDecimal select4Odds = BigDecimal.valueOf(CombinationUtil.c(45, 4))
            .divide(BigDecimal.valueOf(CombinationUtil.c(6, 4)), 2, RoundingMode.DOWN);// C(45,4) / C(6,4)
    private final BigDecimal select5Odds = BigDecimal.valueOf(CombinationUtil.c(45, 5))
            .divide(BigDecimal.valueOf(CombinationUtil.c(6, 5)), 2, RoundingMode.DOWN);// C(45,5) / C(6,5)
    private final BigDecimal select6Odds = BigDecimal.valueOf(CombinationUtil.c(45, 6))
            .divide(BigDecimal.valueOf(CombinationUtil.c(6, 6)), 2, RoundingMode.DOWN);// C(45,6) / C(6,6)

    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        int selectSize = guessNumbers.size();
        long matchCount = guessNumbers.stream().filter(winningNumbers::contains).count();

        switch (selectSize) {
            case 2 -> {
                return matchCount == selectSize ? new WinResult(select2Odds) : LoseResult.getInstance();
            }
            case 3 -> {
                return matchCount == selectSize ? new WinResult(select3Odds) : LoseResult.getInstance();
            }
            case 4 -> {
                return matchCount == selectSize ? new WinResult(select4Odds) : LoseResult.getInstance();
            }
            case 5 -> {
                return matchCount == selectSize ? new WinResult(select5Odds) : LoseResult.getInstance();
            }
            case 6 -> {
                return matchCount == selectSize ? new WinResult(select6Odds) : LoseResult.getInstance();
            }
            default -> {
                return LoseResult.getInstance();
            }
        }
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.SelectN;
    }
}
