package com.cooba.component.numberGenerator;

import com.cooba.enums.LotteryNumberEnum;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SimpleNumberGenerator implements NumberGenerator {
    private final Random random = new SecureRandom();

    @Override
    public List<Integer> generate(LotteryNumberEnum numberEnum) {
        return generate(numberEnum.getMin(), numberEnum.getMax(), numberEnum.getLength(), numberEnum.isRepeat());
    }

    @Override
    public List<Integer> generate(int min, int max, int length, boolean isRepeat) {
        if (isRepeat) {
            return generateRepeatNumbers(min, max, length);
        } else {
            return generateNotRepeatNumbers(min, max, length);
        }
    }

    private List<Integer> generateRepeatNumbers(int min, int max, int length) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            result.add(random.nextInt(max) - min);
        }
        return result;
    }

    private List<Integer> generateNotRepeatNumbers(int min, int max, int length) {
        List<Integer> numbers = IntStream.range(min, max).boxed().collect(Collectors.toList());
        Collections.shuffle(numbers);
        return numbers.subList(0, length);
    }
}
