package fr.gchopin.advent2020.days;

import java.util.List;

import fr.gchopin.advent2020.Day;

public class Day03 extends Day {

    public Day03(String inputFile) {
        super(inputFile);
    }

    private static int countTreesForSlope(List<List<Character>> entries, int right, int down) {

        int height = entries.size();
        int width = entries.get(0).size();

        int countTrees = 0;
        int currentYPosition = 0;

        for (int i = 0; i < height; i = i + down) {
            Character currentChar = entries.get(i).get(currentYPosition);
            if (currentChar.equals('#')) {
                countTrees++;
            }
            currentYPosition = (currentYPosition + right) % width;
        }

        return countTrees;
    }

    @Override
    public void part1() {
        List<List<Character>> entries = _input.getTable();

        int r3d1 = countTreesForSlope(entries, 3, 1);

        System.out.println(r3d1);
    }

    @Override
    public void part2() {
        List<List<Character>> entries = _input.getTable();

        int r1d1 = countTreesForSlope(entries, 1, 1);
        int r3d1 = countTreesForSlope(entries, 3, 1);
        int r5d1 = countTreesForSlope(entries, 5, 1);
        int r7d1 = countTreesForSlope(entries, 7, 1);
        int r1d2 = countTreesForSlope(entries, 1, 2);

        int multiplication = r1d1 * r3d1 * r5d1 * r7d1 * r1d2;
        System.out.println(multiplication);
    }
}
