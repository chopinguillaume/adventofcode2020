package fr.gchopin.advent2020.days;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day09 extends Day {

    public Day09(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<BigInteger> entries = _input.getBigIntegers();

        final int preamble = 25;
        System.out.println(findInvalidNumber(entries, preamble));
    }

    private BigInteger findInvalidNumber(List<BigInteger> entries, final int preamble) {
        for (int i = preamble; i < entries.size(); i++) {

            List<BigInteger> subList = entries.subList(i - preamble, i);
            boolean valid = isValid(entries.get(i), subList);

            if (!valid) {
                return entries.get(i);
            }
        }
        return null;
    }

    boolean isValid(BigInteger candidate, List<BigInteger> previousNumbers) {
        for (int i = 0; i < previousNumbers.size(); i++) {
            for (int j = i + 1; j < previousNumbers.size(); j++) {
                BigInteger sum = previousNumbers.get(i).add(previousNumbers.get(j));
                if (sum.equals(candidate)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void part2() {
        List<BigInteger> entries = _input.getBigIntegers();

        final int preamble = 25;
        BigInteger invalid = findInvalidNumber(entries, preamble);

        System.out.println(findRangeSumsInvalid(entries, invalid));
    }

    private BigInteger findRangeSumsInvalid(List<BigInteger> entries, BigInteger invalid) {
        for (int i = 0; i < entries.size(); i++) {
            BigInteger sum = entries.get(i);
            int idxSum = 0;
            while (sum.compareTo(invalid) < 0) {
                idxSum++;
                sum = sum.add(entries.get(i + idxSum));
            }

            if (sum.compareTo(invalid) == 0) {
                BigInteger min = entries.get(i);
                BigInteger max = entries.get(i);

                for (BigInteger bigint : entries.subList(i, i + idxSum)) {
                    if (bigint.compareTo(min) < 0) {
                        min = bigint;
                    }
                    if (bigint.compareTo(max) > 0) {
                        max = bigint;
                    }
                }
                return min.add(max);
            }
        }
        return null;
    }

}
