package com.cooba.component.playRule;

import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.component.playRule.common.TwoSideCommonMethod;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class SumRule implements PlayRule {
    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        assert guessNumbers.isEmpty();

        Boolean isBig = playParameter.getIsBig();
        Boolean isOdd = playParameter.getIsOdd();

        int total = winningNumbers.stream().reduce(Integer::sum).orElse(0);

        return TwoSideCommonMethod.decideTwoSideResult(isBig, isOdd, total, 175);
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.Sum;
    }
}
