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
public class SelectNNotMatchRule implements PlayRule {
    private final BigDecimal select2NotMatchOdds = BigDecimal.valueOf(CombinationUtil.c(45, 2))
            .divide(BigDecimal.valueOf(CombinationUtil.c(39, 2)), 2, RoundingMode.DOWN);// C(45,2) / C(39,2)
    private final BigDecimal select3NotMatchOdds = BigDecimal.valueOf(CombinationUtil.c(45, 3))
            .divide(BigDecimal.valueOf(CombinationUtil.c(39, 3)), 2, RoundingMode.DOWN);// C(45,3) / C(39,3)
    private final BigDecimal select4NotMatchOdds = BigDecimal.valueOf(CombinationUtil.c(45, 4))
            .divide(BigDecimal.valueOf(CombinationUtil.c(39, 4)), 2, RoundingMode.DOWN);// C(45,4) / C(39,4)
    private final BigDecimal select5NotMatchOdds =BigDecimal.valueOf(CombinationUtil.c(45, 5))
            .divide(BigDecimal.valueOf(CombinationUtil.c(39, 5)), 2, RoundingMode.DOWN);// C(45,5) / C(39,5)
    private final BigDecimal select6NotMatchOdds = BigDecimal.valueOf(CombinationUtil.c(45, 6))
            .divide(BigDecimal.valueOf(CombinationUtil.c(39, 6)), 2, RoundingMode.DOWN);// C(45,6) / C(39,6)
    private final BigDecimal select7NotMatchOdds = BigDecimal.valueOf(CombinationUtil.c(45, 7))
            .divide(BigDecimal.valueOf(CombinationUtil.c(39, 7)), 2, RoundingMode.DOWN);// C(45,7) / C(39,7)


    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        int selectSize = guessNumbers.size();
        long notMatchCount = guessNumbers.stream().filter(number -> !winningNumbers.contains(number)).count();

        switch (selectSize) {
            case 2 -> {
                return notMatchCount == selectSize ? new WinResult(select2NotMatchOdds) : LoseResult.getInstance();
            }
            case 3 -> {
                return notMatchCount == selectSize ? new WinResult(select3NotMatchOdds) : LoseResult.getInstance();
            }
            case 4 -> {
                return notMatchCount == selectSize ? new WinResult(select4NotMatchOdds) : LoseResult.getInstance();
            }
            case 5 -> {
                return notMatchCount == selectSize ? new WinResult(select5NotMatchOdds) : LoseResult.getInstance();
            }
            case 6 -> {
                return notMatchCount == selectSize ? new WinResult(select6NotMatchOdds) : LoseResult.getInstance();
            }
            case 7 -> {
                return notMatchCount == selectSize ? new WinResult(select7NotMatchOdds) : LoseResult.getInstance();
            }
            default -> {
                return LoseResult.getInstance();
            }
        }
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.SelectNNotMatch;
    }
}
