package fr.gchopin.advent2020.days;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.gchopin.advent2020.Day;

public class Day02 extends Day {

    public Day02(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<String> entries = _input.getLines();

        int validPasswords = 0;

        Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

        for (String entry : entries) {
            Matcher matcher = pattern.matcher(entry);
            if (matcher.matches()) {
                int min = Integer.parseInt(matcher.group(1));
                int max = Integer.parseInt(matcher.group(2));
                char letter = matcher.group(3).charAt(0);
                String password = matcher.group(4);

                long count = password.chars().filter((c) -> {
                    return c == letter;
                }).count();
                if (min <= count && count <= max) {
                    validPasswords++;
                }
            }
        }
        System.out.println(validPasswords);
    }

    @Override
    public void part2() {
        List<String> entries = _input.getLines();

        int validPasswords = 0;

        Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

        for (String entry : entries) {
            Matcher matcher = pattern.matcher(entry);
            if (matcher.matches()) {
                int firstPos = Integer.parseInt(matcher.group(1));
                int secondPos = Integer.parseInt(matcher.group(2));
                char letter = matcher.group(3).charAt(0);
                String password = matcher.group(4);

                boolean firstChar = password.charAt(firstPos - 1) == letter;
                boolean secondChar = password.charAt(secondPos - 1) == letter;

                if (firstChar ^ secondChar) {
                    validPasswords++;
                }
            }
        }
        System.out.println(validPasswords);
    }
}
