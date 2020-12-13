package fr.gchopin.advent2020.days;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day10 extends Day {

    public Day10(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<Integer> entries = _input.getIntegers();
        entries.sort(Comparator.naturalOrder());

        int nbOfDiff1 = 0;
        int nbOfDiff3 = 1; // Count the last device already

        int lastAdapter = 0;
        for (Integer adapter : entries) {
            int diff = adapter - lastAdapter;
            lastAdapter = adapter;

            if (diff == 1)
                nbOfDiff1++;
            if (diff == 3)
                nbOfDiff3++;
        }

        System.out.println(nbOfDiff1 * nbOfDiff3);
    }

    @Override
    public void part2() {
        List<Integer> entries = _input.getIntegers();
        entries.add(0);
        entries.add(Collections.max(entries) + 3);
        entries.sort(Comparator.reverseOrder());

        int lastAdapter = entries.get(0);

        int seriesOfOnes = 0;
        List<BigInteger> combinations = new ArrayList<>();
        combinations.add(BigInteger.valueOf(1)); // Only one adapter for the device

        for (Integer adapter : entries) {
            int diff = lastAdapter - adapter;
            lastAdapter = adapter;

            if (diff == 3) {
                seriesOfOnes = 0;
                BigInteger lastCombo = combinations.get(combinations.size() - 1);
                combinations.add(lastCombo);
            }
            if (diff == 1) {
                seriesOfOnes++;
                if (seriesOfOnes == 1) {
                    BigInteger lastCombo = combinations.get(combinations.size() - 1);
                    combinations.add(lastCombo);
                }
                if (seriesOfOnes == 2) {
                    BigInteger lastCombo = combinations.get(combinations.size() - 1);
                    BigInteger lastCombo2 = combinations.get(combinations.size() - 2);
                    combinations.add(lastCombo.add(lastCombo2));
                }
                if (seriesOfOnes >= 3) {
                    BigInteger lastCombo = combinations.get(combinations.size() - 1);
                    BigInteger lastCombo2 = combinations.get(combinations.size() - 2);
                    BigInteger lastCombo3 = combinations.get(combinations.size() - 3);
                    combinations.add(lastCombo.add(lastCombo2.add(lastCombo3)));
                }
            }
        }

        System.out.println(combinations.get(combinations.size() - 1));

    }

}
