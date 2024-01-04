package com.cooba.component.playRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.LoseResult;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class SelectNNotMatchRule implements PlayRule {
    private final BigDecimal select2NotMatchOdds = new BigDecimal("1.33");// C(45,2) / C(39,2)
    private final BigDecimal select3NotMatchOdds = new BigDecimal("1.55");// C(45,3) / C(39,3)
    private final BigDecimal select4NotMatchOdds = new BigDecimal("1.81");// C(45,4) / C(39,4)
    private final BigDecimal select5NotMatchOdds = new BigDecimal("2.12");// C(45,5) / C(39,5)
    private final BigDecimal select6NotMatchOdds = new BigDecimal("2.49");// C(45,6) / C(39,6)
    private final BigDecimal select7NotMatchOdds = new BigDecimal("2.95");// C(45,7) / C(39,7)


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
