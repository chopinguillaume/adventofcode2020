package fr.gchopin.advent2020.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.stream.Collectors;

import fr.gchopin.advent2020.Day;

public class Day07 extends Day {

    public Day07(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        Map<String, Map<String, Integer>> rules = parseRules(_input.getLines());

        // Searching parents
        Stack<String> colorsToSearch = new Stack<>();
        colorsToSearch.push("shiny gold");

        Set<String> acceptedColors = new HashSet<>();
        List<String> alreadySeenColors = new ArrayList<>();

        while (!colorsToSearch.isEmpty()) {
            String color = colorsToSearch.pop();
            alreadySeenColors.add(color);

            List<String> parentColors = getParentColors(rules, color);
            for (String potentialParentColor : parentColors) {
                if (!alreadySeenColors.contains(potentialParentColor)) {
                    colorsToSearch.push(potentialParentColor);
                    acceptedColors.add(potentialParentColor);
                }
            }
        }

        System.out.println(acceptedColors.size());

    }

    private Map<String, Map<String, Integer>> parseRules(List<String> entries) {
        // Map<Parent color, Map<Child color, Quantity>>
        Map<String, Map<String, Integer>> rules = new HashMap<>();

        // Parsing rules
        for (String entry : entries) {
            String[] splitOnContain = entry.split(" contain ");
            String parentColor = splitOnContain[0].replace("bags", "").trim();
            String childrenString = splitOnContain[1];

            HashMap<String, Integer> children = new HashMap<>();

            if (!childrenString.equals("no other bags.")) {
                String[] childrenSplit = childrenString.replaceAll("\\.", "").split(",");
                for (String childString : childrenSplit) {
                    String childTrimmed = childString.trim();
                    int indexOfFirstSpace = childTrimmed.indexOf(" ");
                    int childQuantity = Integer.parseInt(childTrimmed.substring(0, indexOfFirstSpace));
                    String childColor = childTrimmed.substring(indexOfFirstSpace + 1).replaceAll("bag(s)?", "").trim();
                    children.put(childColor, childQuantity);
                }
            }
            rules.put(parentColor, children);
        }
        return rules;
    }

    private List<String> getParentColors(Map<String, Map<String, Integer>> rules, String childColor) {

        return rules.entrySet().stream().filter((rule) -> rule.getValue().containsKey(childColor))
                .map((rule) -> rule.getKey()).collect(Collectors.toList());
    }

    @Override
    public void part2() {
        Map<String, Map<String, Integer>> rules = parseRules(_input.getLines());

        int numberOfBags = getNumberOfBags(rules, "shiny gold");

        System.out.println(numberOfBags);
    }

    private int getNumberOfBags(Map<String, Map<String, Integer>> rules, String parentColor) {
        int total = 0;

        Map<String, Integer> rule = rules.get(parentColor);
        for (Entry<String, Integer> children : rule.entrySet()) {
            total += children.getValue() * (1 + getNumberOfBags(rules, children.getKey()));
        }

        return total;
    }

}
