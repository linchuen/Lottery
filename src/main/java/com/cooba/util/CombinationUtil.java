package com.cooba.util;

public class CombinationUtil {

    public static int c(int n, int select) {
        if (n == select) return 1;

        if (select > n / 2) {
            select = n - select;
        }

        int count = 1;
        for (int i = 0; i < select; i++) {
            count = count * (n - i) / (i + 1);
        }
        return count;
    }
}
