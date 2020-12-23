package fr.gchopin.advent2020.days;

import fr.gchopin.advent2020.Day;

public class Day15 extends Day {

    Integer[] input;

    public Day15(Integer... input) {
        super();
        this.input = input;
    }

    @Override
    public void part1() {

        runUntilXTurns(2020);
    }

    private void runUntilXTurns(int maxTurns) {
        // An array is faster than a Map
        int[] seenNumbers = new int[maxTurns];

        // Initialize with turn 0
        int lastNumber = input[0];

        // Iterate over 2020 turns
        for (int turn = 1; turn < maxTurns; turn++) {
            int newNumber;
            if (turn < input.length) {
                newNumber = input[turn];
            } else {
                int lastTurnOfNumber = seenNumbers[lastNumber];

                if (lastTurnOfNumber != 0) {
                    newNumber = turn - lastTurnOfNumber;
                } else {
                    newNumber = 0;
                }
            }
            // Here we know our new number, so we can store the previous one
            seenNumbers[lastNumber] = turn;
            // We prepare the next loop by making our new number the next 'previous one'
            lastNumber = newNumber;
        }

        // Print the 2020th number
        System.out.println(lastNumber);
    }

    @Override
    public void part2() {
        runUntilXTurns(30000000);
    }

}
