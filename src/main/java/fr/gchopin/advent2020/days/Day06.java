package fr.gchopin.advent2020.days;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.gchopin.advent2020.Day;

public class Day06 extends Day {

    public Day06(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<String> lines = _input.getLines();

        int totalYes = 0;

        Set<Character> groupAnswers = new HashSet<>();
        for (String line : lines) {
            if (line.isEmpty()) {
                totalYes += groupAnswers.size();
                groupAnswers = new HashSet<>();
                continue;
            }
            for (Character character : line.toCharArray()) {
                groupAnswers.add(character);
            }
        }
        // Last group in case last line is not a new line
        if (groupAnswers.size() > 0) {
            totalYes += groupAnswers.size();
        }

        System.out.println(totalYes);
    }

    @Override
    public void part2() {
        List<String> lines = _input.getLines();

        int totalYes = 0;

        Set<Character> groupAnswers = new HashSet<>();
        boolean firstPerson = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                totalYes += groupAnswers.size();
                groupAnswers = new HashSet<>();
                firstPerson = true;
                continue;
            }

            Set<Character> personAnswers = new HashSet<>();
            for (Character character : line.toCharArray()) {
                personAnswers.add(character);
            }
            if (firstPerson) {
                firstPerson = false;
                groupAnswers.addAll(personAnswers);
            } else {
                groupAnswers.removeIf((answer) -> !personAnswers.contains(answer));
            }
        }
        // Last group in case last line is not a new line
        if (groupAnswers.size() > 0) {
            totalYes += groupAnswers.size();
        }

        System.out.println(totalYes);
    }
}
