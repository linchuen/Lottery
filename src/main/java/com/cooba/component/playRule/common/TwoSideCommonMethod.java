package com.cooba.component.playRule.common;

import com.cooba.object.LoseResult;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TwoSideCommonMethod {
    private final BigDecimal twoFactorOdds = new BigDecimal("4");// 2 * 2
    private final BigDecimal oneFactorOdds = new BigDecimal("2");// 2

    public PlayResult decideTwoSideResult(Boolean isBig, Boolean isOdd, int number, int bigStandard) {
        boolean bigNumber = number >= bigStandard;
        boolean smallNumber = !bigNumber;
        boolean oddNumber = number % 2 == 1;
        boolean evenNumber = !oddNumber;
        if (isBig != null && isOdd != null) {
            if (isBig && isOdd) {
                return bigNumber && oddNumber ? new WinResult(twoFactorOdds) : LoseResult.getInstance();
            } else if (isBig) {
                return bigNumber && evenNumber ? new WinResult(twoFactorOdds) : LoseResult.getInstance();
            } else if (isOdd) {
                return smallNumber && oddNumber ? new WinResult(twoFactorOdds) : LoseResult.getInstance();
            } else {
                return smallNumber && evenNumber ? new WinResult(twoFactorOdds) : LoseResult.getInstance();
            }
        } else if (isBig != null) {
            if (isBig) {
                return bigNumber ? new WinResult(oneFactorOdds) : LoseResult.getInstance();
            } else {
                return smallNumber ? new WinResult(oneFactorOdds) : LoseResult.getInstance();
            }
        } else if (isOdd != null) {
            if (isOdd) {
                return oddNumber ? new WinResult(oneFactorOdds) : LoseResult.getInstance();
            } else {
                return evenNumber ? new WinResult(oneFactorOdds) : LoseResult.getInstance();
            }
        } else {
            return LoseResult.getInstance();
        }
    }
}
