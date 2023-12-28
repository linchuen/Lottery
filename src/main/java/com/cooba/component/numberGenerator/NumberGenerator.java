package com.cooba.component.numberGenerator;

import java.util.List;

public interface NumberGenerator {
    List<Integer> generate(int min, int max, int length, boolean isRepeat);
}
