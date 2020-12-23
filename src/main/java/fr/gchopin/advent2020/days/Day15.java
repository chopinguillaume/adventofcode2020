package fr.gchopin.advent2020.days;

import java.util.HashMap;
import java.util.Map;

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
        Map<Integer, Integer> seenNumbers = new HashMap<>(); // Key is the number, Value is the last turn

        // Initialize with turn 0
        int lastNumber = input[0];

        // Iterate over 2020 turns
        for (int turn = 1; turn < maxTurns; turn++) {
            int newNumber;
            if (turn < input.length) {
                newNumber = input[turn];
            } else {
                if (seenNumbers.containsKey(lastNumber)) {
                    int lastTurnOfNumber = seenNumbers.get(lastNumber);
                    int lastTurn = turn - 1;
                    newNumber = lastTurn - lastTurnOfNumber;
                } else {
                    newNumber = 0;
                }
            }
            // Here we know our new number, so we can store the previous one
            // System.out.println(turn + " : " + lastNumber);
            seenNumbers.put(lastNumber, turn - 1);
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
