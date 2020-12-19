package fr.gchopin.advent2020.days;

import java.util.ArrayList;
import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day11 extends Day {

    public Day11(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<List<Character>> initialTable = _input.getTable();

        while (true) {
            List<List<Character>> table = nextState(initialTable, PartNumber.Part1);

            if (statesAreEqual(initialTable, table)) {
                int occupied = numberOfOccupiedSeats(table);
                System.out.println(occupied);
                return;
            }
            initialTable = table;
        }
    }

    private int numberOfOccupiedSeats(List<List<Character>> table) {
        int occupied = 0;

        for (int i = 0; i < table.size(); i++) {
            for (int j = 0; j < table.get(i).size(); j++) {
                Character c = table.get(i).get(j);
                if (c == '#') {
                    occupied++;
                }
            }
        }

        return occupied;
    }

    private boolean statesAreEqual(List<List<Character>> firstTable, List<List<Character>> secondTable) {
        for (int i = 0; i < firstTable.size(); i++) {
            for (int j = 0; j < firstTable.get(i).size(); j++) {
                Character first = firstTable.get(i).get(j);
                Character second = secondTable.get(i).get(j);

                if (first != second) {
                    return false;
                }
            }
        }

        return true;
    }

    private List<List<Character>> nextState(List<List<Character>> initialTable, PartNumber partNumber) {

        List<List<Character>> newTable = new ArrayList<>();

        for (int i = 0; i < initialTable.size(); i++) {
            newTable.add(new ArrayList<>());

            for (int j = 0; j < initialTable.get(i).size(); j++) {
                Character currentSpot = initialTable.get(i).get(j);

                if (currentSpot == '.') {
                    newTable.get(i).add('.');
                    continue;
                }

                List<Character> adjacentSpots;
                switch (partNumber) {
                    case Part1:
                        adjacentSpots = adjacentSpots1(initialTable, i, j);
                        break;
                    case Part2:
                        adjacentSpots = adjacentSpots2(initialTable, i, j);
                        break;
                    default:
                        throw new IllegalStateException();
                }

                if (currentSpot == 'L') {
                    boolean anySeatTaken = adjacentSpots.stream().anyMatch(c -> c == '#');
                    if (!anySeatTaken) {
                        newTable.get(i).add('#');
                    } else {
                        newTable.get(i).add('L');
                    }
                } else if (currentSpot == '#') {
                    long numberAdjacentTaken = adjacentSpots.stream().filter(c -> c == '#').count();
                    int maxTakenToChange;
                    switch (partNumber) {
                        case Part1:
                            maxTakenToChange = 4;
                            break;
                        case Part2:
                            maxTakenToChange = 5;
                            break;
                        default:
                            throw new IllegalStateException();
                    }
                    if (numberAdjacentTaken >= maxTakenToChange) {
                        newTable.get(i).add('L');
                    } else {
                        newTable.get(i).add('#');
                    }
                }

            }
        }

        return newTable;
    }

    private List<Character> adjacentSpots1(List<List<Character>> initialTable, int i, int j) {
        List<Character> adjacentSpots = new ArrayList<>();

        if (i > 0) {
            if (j > 0) {
                adjacentSpots.add(initialTable.get(i - 1).get(j - 1));
            }
            adjacentSpots.add(initialTable.get(i - 1).get(j));
            if (j < initialTable.get(i).size() - 1) {
                adjacentSpots.add(initialTable.get(i - 1).get(j + 1));
            }
        }

        if (j > 0) {
            adjacentSpots.add(initialTable.get(i).get(j - 1));
        }
        if (j < initialTable.get(i).size() - 1) {
            adjacentSpots.add(initialTable.get(i).get(j + 1));
        }

        if (i < initialTable.size() - 1) {
            if (j > 0) {
                adjacentSpots.add(initialTable.get(i + 1).get(j - 1));
            }
            adjacentSpots.add(initialTable.get(i + 1).get(j));
            if (j < initialTable.get(i).size() - 1) {
                adjacentSpots.add(initialTable.get(i + 1).get(j + 1));
            }
        }

        return adjacentSpots;
    }

    @Override
    public void part2() {
        List<List<Character>> initialTable = _input.getTable();

        while (true) {
            List<List<Character>> table = nextState(initialTable, PartNumber.Part2);

            if (statesAreEqual(initialTable, table)) {
                int occupied = numberOfOccupiedSeats(table);
                System.out.println(occupied);
                return;
            }
            initialTable = table;
        }
    }

    private List<Character> adjacentSpots2(List<List<Character>> table, int i, int j) {
        List<Character> adjacentSpots = new ArrayList<>();

        int shift;

        // LEFT : j goes down
        for (shift = 1; j - shift >= 0; shift++) {
            Character c = table.get(i).get(j - shift);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // RIGHT : j goes up
        for (shift = 1; j + shift < table.get(i).size(); shift++) {
            Character c = table.get(i).get(j + shift);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // UP : i goes down
        for (shift = 1; i - shift >= 0; shift++) {
            Character c = table.get(i - shift).get(j);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // DOWN : i goes up
        for (shift = 1; i + shift < table.size(); shift++) {
            Character c = table.get(i + shift).get(j);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // LEFT & UP : i goes down & j goes down
        for (shift = 1; i - shift >= 0 && j - shift >= 0; shift++) {
            Character c = table.get(i - shift).get(j - shift);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // RIGHT & UP : i goes down & j goes up
        for (shift = 1; i - shift >= 0 && j + shift < table.get(i).size(); shift++) {
            Character c = table.get(i - shift).get(j + shift);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // LEFT & DOWN : i goes up & j goes down
        for (shift = 1; i + shift < table.size() && j - shift >= 0; shift++) {
            Character c = table.get(i + shift).get(j - shift);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }

        // RIGHT & DOWN : i goes up & j goes up
        for (shift = 1; i + shift < table.size() && j + shift < table.get(i).size(); shift++) {
            Character c = table.get(i + shift).get(j + shift);
            if (c != '.') {
                adjacentSpots.add(c);
                break;
            }
        }
        return adjacentSpots;
    }

}
