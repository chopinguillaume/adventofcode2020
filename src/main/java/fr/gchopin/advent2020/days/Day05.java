package fr.gchopin.advent2020.days;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import fr.gchopin.advent2020.Day;

public class Day05 extends Day {

    public Day05(String inputFile) {
        super(inputFile);
    }

    private static int computeSeatId(String entry) {
        int row = row(entry);
        int col = column(entry);
        return row * 8 + col;
    }

    private static int row(String entry) {
        String zone = entry.substring(0, 7);
        return computeRowOrColumn(zone, 0, 127, 'F', 'B');
    }

    private static int column(String entry) {
        String zone = entry.substring(7, 10);
        return computeRowOrColumn(zone, 0, 7, 'L', 'R');
    }

    private static int computeRowOrColumn(String entry, int min, int max, char lowerHalf, char upperHalf) {

        for (char c : entry.toCharArray()) {
            int rangeSize = (int) Math.ceil(((double) max - min) / 2);
            if (c == lowerHalf) {
                max -= rangeSize;
            } else if (c == upperHalf) {
                min += rangeSize;
            } else {
                throw new IllegalArgumentException("Illegal character in input");
            }
        }

        if (min == max) {
            return min;
        } else {
            throw new IllegalStateException("No value found");
        }
    }

    @Override
    public void part1() {
        List<String> entries = _input.getLines();

        int maxId = 0;
        for (String entry : entries) {
            int seatId = computeSeatId(entry);
            if (seatId > maxId) {
                maxId = seatId;
            }
        }
        System.out.println(maxId);
    }

    @Override
    public void part2() {
        List<String> entries = _input.getLines();

        List<Integer> listIds = entries.stream().map((entry) -> computeSeatId(entry)).collect(Collectors.toList());

        listIds.sort(Comparator.naturalOrder());

        for (int i = 1; i < listIds.size(); i++) {
            int idBefore = listIds.get(i - 1);
            int idCurrent = listIds.get(i);

            if (idBefore + 1 != idCurrent) {
                System.out.println(idBefore + 1);
                return;
            }
        }
    }
}
