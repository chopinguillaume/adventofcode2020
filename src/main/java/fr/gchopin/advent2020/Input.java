package fr.gchopin.advent2020;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Input {

    private final String inputFile;

    public Input(String inputFile) {
        if (!inputFile.startsWith("/")) {
            this.inputFile = "/" + inputFile;
        } else {
            this.inputFile = inputFile;
        }
    }

    public List<String> getLines() {
        List<String> liste = new ArrayList<>();

        Scanner scanner = new Scanner(getClass().getResourceAsStream(inputFile));
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            liste.add(nextLine);
        }

        scanner.close();

        return liste;
    }

    public List<Integer> getIntegers() {
        List<Integer> liste = new ArrayList<>();

        try (InputStream resourceAsStream = getClass().getResourceAsStream(inputFile)) {
            if (resourceAsStream == null) {
                throw new IllegalArgumentException("Input file not found : " + inputFile);
            }
            Scanner scanner = new Scanner(resourceAsStream);
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                liste.add(Integer.parseInt(nextLine));
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return liste;
    }

    public List<List<Character>> getTable() {
        List<List<Character>> table = new ArrayList<>();

        Scanner scanner = new Scanner(getClass().getResourceAsStream(inputFile));
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            List<Character> chars = nextLine.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
            table.add(chars);
        }

        scanner.close();

        return table;
    }
}
