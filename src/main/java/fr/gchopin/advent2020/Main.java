package fr.gchopin.advent2020;

import fr.gchopin.advent2020.days.*;

class Main {
    public static void main(String[] args) {
        // (new Day01("input-01.txt")).run();
        // (new Day02("input-02.txt")).run();
        // (new Day03("input-03.txt")).run();
        // (new Day04("input-04.txt")).run();
        // (new Day05("input-05.txt")).run();
        // (new Day06("input-06.txt")).run();
        // (new Day07("input-07.txt")).run();
        // (new Day08("input-08.txt")).run();
        // (new Day09("input-09.txt")).run();
        // (new Day10("input-10-test.txt")).run();
        // (new Day10("input-10-test2.txt")).run();
        // (new Day10("input-10.txt")).run();
        // (new Day11("input-11-test.txt")).run();
        // (new Day11("input-11.txt")).run();
        // (new Day12("input-12-test.txt")).run();
        // (new Day12("input-12.txt")).run();
        // (new Day13("input-13-test.txt")).run();
        // (new Day13("input-13-test2.txt")).run();
        // (new Day13("input-13.txt")).run();
        // (new Day14("input-14-test.txt")).run();
        // (new Day14("input-14-test2.txt")).run();
        // (new Day14("input-14.txt")).run();
        (new Day15(0, 3, 6)).run();
        (new Day15(1, 3, 2)).run();
        (new Day15(2, 1, 3)).run();
        (new Day15(1, 2, 3)).run();
        (new Day15(2, 3, 1)).run();
        (new Day15(3, 2, 1)).run();
        (new Day15(3, 1, 2)).run();
        (new Day15(2, 1, 10, 11, 0, 6)).run();
    }
}