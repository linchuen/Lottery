package com.cooba.method;

import com.cooba.object.LoseResult;
import com.cooba.object.PlayResult;
import com.cooba.object.WinResult;

public class TwoSideCommonMethod {

    public static PlayResult decideTwoSideResult(Boolean isBig, Boolean isOdd, int number, int bigStandard) {
        boolean bigNumber = number >= bigStandard;
        boolean smallNumber = !bigNumber;
        boolean oddNumber = number % 2 == 1;
        boolean evenNumber = !oddNumber;
        if (isBig != null && isOdd != null) {
            if (isBig && isOdd) {
                return bigNumber && oddNumber ? new WinResult() : LoseResult.getInstance();
            } else if (isBig) {
                return bigNumber && evenNumber ? new WinResult() : LoseResult.getInstance();
            } else if (isOdd) {
                return smallNumber && oddNumber ? new WinResult() : LoseResult.getInstance();
            } else {
                return smallNumber && evenNumber ? new WinResult() : LoseResult.getInstance();
            }
        } else if (isBig != null) {
            if (isBig) {
                return bigNumber ? new WinResult() : LoseResult.getInstance();
            } else {
                return smallNumber ? new WinResult() : LoseResult.getInstance();
            }
        } else if (isOdd != null) {
            if (isOdd) {
                return oddNumber ? new WinResult() : LoseResult.getInstance();
            } else {
                return evenNumber ? new WinResult() : LoseResult.getInstance();
            }
        } else {
            return LoseResult.getInstance();
        }
    }
}
