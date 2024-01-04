package com.cooba.component.playRule;

import com.cooba.component.playRule.common.TwoSideCommonMethod;
import com.cooba.enums.GameRuleEnum;
import com.cooba.object.PlayParameter;
import com.cooba.object.PlayResult;
import com.cooba.object.TieResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionNTwoSideRule implements PlayRule {
    private final TwoSideCommonMethod commonMethod;

    @Override
    public PlayResult decideResult(List<Integer> winningNumbers, List<Integer> guessNumbers, PlayParameter playParameter) {
        assert guessNumbers.isEmpty();
        assert playParameter.getPosition() != null;

        int position = playParameter.getPosition();
        Boolean isBig = playParameter.getIsBig();
        Boolean isOdd = playParameter.getIsOdd();

        int number = winningNumbers.get(position - 1);
        if (number == 49) return new TieResult();

        return commonMethod.decideTwoSideResult(isBig, isOdd, number, 25);
    }

    @Override
    public GameRuleEnum getGameRuleEnum() {
        return GameRuleEnum.PositionNTwoSide;
    }
}
