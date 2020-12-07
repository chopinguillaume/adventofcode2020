package fr.gchopin.advent2020.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gchopin.advent2020.Day;

public class Day04 extends Day {

    public Day04(String inputFile) {
        super(inputFile);
    }

    private static List<String> correctColorsList = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    private static List<Map<String, String>> buildPassportList(List<String> entries) {
        List<Map<String, String>> listOfMaps = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (String line : entries) {
            if (!line.isEmpty()) {
                String[] splitOnSpace = line.split(" ");
                for (String entry : splitOnSpace) {
                    String[] passportEntry = entry.split(":");
                    map.put(passportEntry[0], passportEntry[1]);
                }
            } else {
                listOfMaps.add(map);
                map = new HashMap<>();
            }
        }
        // Last map in case the last line is not a new line
        if (map.size() != 0) {
            listOfMaps.add(map);
        }
        return listOfMaps;
    }

    private static boolean validatePassportId(String pid) {
        if (pid == null) {
            return false;
        }
        return pid.matches("^\\d{9}$");
    }

    private static boolean validateEyeColor(String ecl) {
        if (ecl == null) {
            return false;
        }
        return correctColorsList.contains(ecl);
    }

    private static boolean validateHairColor(String hcl) {
        if (hcl == null) {
            return false;
        }
        if (!hcl.startsWith("#")) {
            return false;
        }
        String color = hcl.substring(1);
        return color.matches("^[0-9a-f]{6}$");
    }

    private static boolean validateHeight(String hgt) {
        if (hgt == null) {
            return false;
        }
        if (hgt.endsWith("in")) {
            String heightStr = hgt.substring(0, hgt.length() - 2);
            int height = Integer.parseInt(heightStr);
            if (59 <= height && height <= 76) {
                return true;
            }
        }
        if (hgt.endsWith("cm")) {
            String heightStr = hgt.substring(0, hgt.length() - 2);
            int height = Integer.parseInt(heightStr);
            if (150 <= height && height <= 193) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateYear(String field, int min, int max) {
        if (field == null) {
            return false;
        }
        try {
            int year = Integer.parseInt(field);
            return min <= year && year <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void part1() {
        List<String> entries = _input.getLines();

        List<Map<String, String>> passports = buildPassportList(entries);

        String[] mandatoryEntries = { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid" };
        int numberValid = 0;

        for (Map<String, String> passport : passports) {
            boolean passportIsValid = true;

            for (String mandatoryEntry : mandatoryEntries) {
                passportIsValid = passportIsValid && passport.containsKey(mandatoryEntry);
            }

            if (passportIsValid) {
                numberValid++;
            }
        }
        System.out.println(numberValid);

    }

    @Override
    public void part2() {
        List<String> entries = _input.getLines();

        List<Map<String, String>> passports = buildPassportList(entries);
        int numberValid = 0;

        for (Map<String, String> passport : passports) {

            boolean byrValid = validateYear(passport.get("byr"), 1920, 2002);

            boolean iyrValid = validateYear(passport.get("iyr"), 2010, 2020);

            boolean eyrValid = validateYear(passport.get("eyr"), 2020, 2030);

            boolean hgtValid = validateHeight(passport.get("hgt"));

            boolean hclValid = validateHairColor(passport.get("hcl"));

            boolean eclValid = validateEyeColor(passport.get("ecl"));

            boolean pidValid = validatePassportId(passport.get("pid"));

            if (byrValid && iyrValid && eyrValid && hgtValid && hclValid && eclValid && pidValid) {
                numberValid++;
            }
        }

        System.out.println(numberValid);
    }

}