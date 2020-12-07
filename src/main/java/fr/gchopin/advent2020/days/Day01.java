package fr.gchopin.advent2020.days;

import java.util.Comparator;
import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day01 extends Day {

    public Day01(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<Integer> entries = _input.getIntegers();
        entries.sort(Comparator.naturalOrder());

        for (int i = 0; i < entries.size(); i++) {
            Integer firstValue = entries.get(i);
            for (int j = i + 1; j < entries.size(); j++) {
                Integer secondValue = entries.get(j);
                Integer sum = firstValue + secondValue;
                if (sum == 2020) {
                    Integer multiplication = firstValue * secondValue;
                    System.out.println(String.format("%d * %d = %d", firstValue, secondValue, multiplication));
                    return;
                }
                if (sum > 2020) {
                    continue;
                }
            }
        }
    }

    @Override
    public void part2() {
        List<Integer> entries = _input.getIntegers();
        entries.sort(Comparator.naturalOrder());

        for (int i = 0; i < entries.size(); i++) {
            Integer firstValue = entries.get(i);
            for (int j = i + 1; j < entries.size(); j++) {
                Integer secondValue = entries.get(j);
                for (int h = j + 1; h < entries.size(); h++) {
                    Integer thirdValue = entries.get(h);
                    Integer sum = firstValue + secondValue + thirdValue;
                    if (sum == 2020) {
                        Integer multiplication = firstValue * secondValue * thirdValue;
                        System.out.println(String.format("%d * %d * %d = %d", firstValue, secondValue, thirdValue,
                                multiplication));
                        return;
                    }
                    if (sum > 2020) {
                        continue;
                    }
                }
            }
        }
    }
}
