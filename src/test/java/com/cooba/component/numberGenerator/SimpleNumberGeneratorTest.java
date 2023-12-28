package com.cooba.component.numberGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class SimpleNumberGeneratorTest {
    @Spy
    SimpleNumberGenerator simpleNumberGenerator;

    @Test
    void generateLuckyTen() {
        for (int i = 0; i < 100; i++) {
            List<Integer> numbers = simpleNumberGenerator.generate(0,9,5,true);
            System.out.println(numbers);
        }
    }

    @Test
    void generateMarkSix() {
        for (int i = 0; i < 100; i++) {
            List<Integer> numbers = simpleNumberGenerator.generate(1,45,7,false);
            System.out.println(numbers);
        }
    }
}