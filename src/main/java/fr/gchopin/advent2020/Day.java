package fr.gchopin.advent2020;

import java.time.Duration;
import java.time.Instant;

public abstract class Day {
    protected final Input _input;

    public Day(String inputFile) {
        _input = new Input(inputFile);
    }

    public Day() {
        _input = null;
    }

    public abstract void part1();

    public abstract void part2();

    public void run() {
        System.out.println(String.format("Running %s", this.getClass().getName()));

        Instant start1 = Instant.now();
        this.part1();
        Instant end1 = Instant.now();
        long runtimePart1 = Duration.between(start1, end1).toMillis();
        System.out.println(String.format("Part 1 took %d ms", runtimePart1));

        Instant start2 = Instant.now();
        this.part2();
        Instant end2 = Instant.now();
        long runtimePart2 = Duration.between(start2, end2).toMillis();
        System.out.println(String.format("Part 2 took %d ms", runtimePart2));
    }

    protected enum PartNumber {
        Part1, Part2;
    }
}