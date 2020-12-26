package fr.gchopin.advent2020.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fr.gchopin.advent2020.Day;

public class Day16 extends Day {

    public Day16(String input) {
        super(input);
    }

    @Override
    public void part1() {
        List<String> lines = _input.getLines();

        List<Rule> rules = parseRules(lines);
        List<Ticket> otherTickets = parseOtherTickets(lines);

        int sumInvalid = 0;
        for (Ticket ticket : otherTickets) {
            sumInvalid += sumInvalidValues(rules, ticket);
        }
        System.out.println(sumInvalid);

    }

    private int sumInvalidValues(List<Rule> rules, Ticket ticket) {

        int sumInvalidValues = 0;

        for (Integer value : ticket.values) {
            boolean isValid = false;

            for (int i = 0; i < rules.size() && !isValid; i++) {
                isValid = rules.get(i).containsValue(value);
            }

            if (!isValid) {
                sumInvalidValues += value;
            }
        }

        return sumInvalidValues;

    }

    private List<Ticket> parseOtherTickets(List<String> lines) {
        int i = 0;

        // Skip the lines we don't care about
        while (i < lines.size()) {
            String line = lines.get(i);
            i++;
            if (line.equals("nearby tickets:")) {
                break;
            }
        }
        // i is the index of the first line we want

        List<Ticket> tickets = new ArrayList<>();

        for (int j = i; j < lines.size(); j++) {
            String ticketLine = lines.get(j);
            Ticket ticket = new Ticket();
            ticket.setValues(ticketLine.split(","));
            tickets.add(ticket);
        }

        return tickets;
    }

    private Ticket parseMyTicket(List<String> lines) {
        int i = 0;

        // Skip the lines we don't care about
        while (i < lines.size()) {
            String line = lines.get(i);
            i++;
            if (line.equals("your ticket:")) {
                break;
            }
        }
        // i is the index of the line we want

        String ticketLine = lines.get(i);
        Ticket myTicket = new Ticket();
        myTicket.setValues(ticketLine.split(","));

        return myTicket;
    }

    private List<Rule> parseRules(List<String> lines) {
        List<Rule> rules = new ArrayList<>();

        for (String line : lines) {
            if (line.equals("")) {
                break;
            }

            String[] split = line.split(":");
            String field = split[0];
            String[] split2 = split[1].split("( or )|(-)");

            Rule rule = new Rule();
            rule.setField(field);
            rule.setFirstMin(Integer.parseInt(split2[0].trim()));
            rule.setFirstMax(Integer.parseInt(split2[1].trim()));
            rule.setSecondMin(Integer.parseInt(split2[2].trim()));
            rule.setSecondMax(Integer.parseInt(split2[3].trim()));

            rules.add(rule);
        }

        return rules;
    }

    @Override
    public void part2() {
        List<String> lines = _input.getLines();

        List<Rule> rules = parseRules(lines);
        Ticket myTicket = parseMyTicket(lines);
        List<Ticket> otherTickets = parseOtherTickets(lines);

        // Invalid tickets are not useful and must be filtered out
        otherTickets = otherTickets.stream().filter(t -> isTicketValid(rules, t)).collect(Collectors.toList());

        // ruleMapping contains the correct positions for each rule
        Map<Rule, List<Integer>> ruleMapping = new HashMap<>();
        int positionCount = myTicket.values.size();

        // Each position is correct for each rule by default
        for (Rule rule : rules) {
            List<Integer> potentialPositions = new ArrayList<>();
            for (int pos = 0; pos < positionCount; pos++) {
                potentialPositions.add(pos);
            }
            ruleMapping.put(rule, potentialPositions);
        }

        // printMapping(ruleMapping);
        iterateRuleMappings(ruleMapping, otherTickets);

        cleanRuleMappings(ruleMapping);

        long departure = getDepartureValues(ruleMapping, myTicket);
        System.out.println(departure);
    }

    private long getDepartureValues(Map<Rule, List<Integer>> ruleMapping, Ticket myTicket) {
        long multiplication = 1;

        for (Entry<Rule, List<Integer>> entry : ruleMapping.entrySet()) {
            String field = entry.getKey().field;
            if (field.startsWith("departure")) {
                int position = entry.getValue().get(0);
                int value = myTicket.values.get(position);
                multiplication *= value;
            }
        }

        return multiplication;
    }

    private void cleanRuleMappings(Map<Rule, List<Integer>> ruleMapping) {
        while (!allPositionsFound(ruleMapping)) {
            // printMapping(ruleMapping);
            cleanIfOneRuleHasOnlyOnePosition(ruleMapping);
            cleanIfOnePositionHasOnlyOneRule(ruleMapping);
        }
        // printMapping(ruleMapping);
    }

    private void printMapping(Map<Rule, List<Integer>> ruleMapping) {
        int nb = ruleMapping.keySet().size();
        for (Entry<Rule, List<Integer>> entry : ruleMapping.entrySet()) {
            System.out.print(String.format("%40s", entry.getKey()) + " | ");
            for (int i = 0; i < nb; i++) {
                String val = entry.getValue().contains(i) ? "" + i : ".";
                System.out.print(String.format("%2s", val) + " ");
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    private void cleanIfOnePositionHasOnlyOneRule(Map<Rule, List<Integer>> ruleMapping) {
        // The number of positions is equal to the number of rules
        int numberOfRules = ruleMapping.keySet().size();

        for (int position = 0; position < numberOfRules; position++) {
            Rule onlyRule = getOnlyRuleWithPosition(position, ruleMapping);
            if (onlyRule != null) {
                ruleMapping.get(onlyRule).clear();
                ruleMapping.get(onlyRule).add(position);
            }
        }
    }

    private Rule getOnlyRuleWithPosition(Integer position, Map<Rule, List<Integer>> ruleMapping) {
        Rule onlyRule = null;
        for (Entry<Rule, List<Integer>> entry : ruleMapping.entrySet()) {
            List<Integer> positions = entry.getValue();
            if (positions.contains(position)) {
                if (onlyRule == null) {
                    onlyRule = entry.getKey();
                } else {
                    // Multiple rules have the same position
                    return null;
                }
            }
        }
        // Either no rule has the position (should not be possible), or only one
        return onlyRule;
    }

    private void cleanIfOneRuleHasOnlyOnePosition(Map<Rule, List<Integer>> ruleMapping) {
        for (Entry<Rule, List<Integer>> entry : ruleMapping.entrySet()) {
            Rule rule = entry.getKey();
            List<Integer> positions = entry.getValue();

            if (positions.size() == 1) {
                int onlyPossiblePosition = positions.get(0);

                // Remove this position from all other rules
                removePositionForEachRuleExcept(ruleMapping, onlyPossiblePosition, rule);
            }
        }
    }

    private void removePositionForEachRuleExcept(Map<Rule, List<Integer>> ruleMapping, Integer onlyPossiblePosition,
            Rule exception) {

        for (Entry<Rule, List<Integer>> entry : ruleMapping.entrySet()) {
            if (entry.getKey().equals(exception)) {
                continue;
            }
            entry.getValue().remove(onlyPossiblePosition);
            if (entry.getValue().size() == 0) {
                throw new IllegalStateException("This rule has now no possible position : " + entry.getKey());
            }
        }
    }

    private void iterateRuleMappings(Map<Rule, List<Integer>> ruleMapping, List<Ticket> tickets) {
        for (Entry<Rule, List<Integer>> mapping : ruleMapping.entrySet()) {
            Rule rule = mapping.getKey();
            List<Integer> positions = mapping.getValue();
            // We test each remaining possible position for the rule
            Iterator<Integer> positionsIterator = positions.iterator();
            while (positionsIterator.hasNext()) {
                Integer position = positionsIterator.next();
                // For each ticket, we check if the value at the current position matches the
                // rule
                // If not, the rule cannot be applied to this position and we remove it from the
                // map
                for (Ticket ticket : tickets) {
                    int val = ticket.values.get(position);
                    boolean isValueValid = rule.containsValue(val);
                    if (!isValueValid) {
                        // This position is not valid for this rule
                        positionsIterator.remove();
                        break;
                    }
                }
            }
        }
    }

    // All positions are found when all rules have only one possible position
    private boolean allPositionsFound(Map<Rule, List<Integer>> ruleMapping) {
        boolean allFound = true;

        for (Entry<Rule, List<Integer>> entry : ruleMapping.entrySet()) {
            List<Integer> val = entry.getValue();
            if (val.size() == 0) {
                throw new IllegalStateException("This rule has no valid position " + entry.getKey());
            }
            allFound = allFound && val.size() == 1;
        }

        return allFound;
    }

    private boolean isTicketValid(List<Rule> rules, Ticket ticket) {
        for (Integer value : ticket.values) {
            boolean valueValid = false;

            for (Rule rule : rules) {
                valueValid = valueValid || rule.containsValue(value);
            }

            if (!valueValid) {
                return false;
            }
        }
        return true;
    }

    class Rule {
        String field;
        Integer firstMin;
        Integer firstMax;
        Integer secondMin;
        Integer secondMax;

        public void setFirstMin(int min) {
            firstMin = min;
        }

        public void setField(String field) {
            this.field = field;
        }

        public void setFirstMax(int max) {
            firstMax = max;
        }

        public void setSecondMin(int min) {
            secondMin = min;
        }

        public void setSecondMax(int max) {
            secondMax = max;
        }

        public boolean containsValue(Integer value) {
            return (firstMin <= value && value <= firstMax) || (secondMin <= value && value <= secondMax);
        }

        @Override
        public String toString() {
            return field + ": " + firstMin + "-" + firstMax + " or " + secondMin + "-" + secondMax;
        }

    }

    class Ticket {
        List<Integer> values;
        List<String> fields = new ArrayList<>();

        public void setValues(String[] split) {
            values = new ArrayList<>();
            for (String val : split) {
                values.add(Integer.parseInt(val));
            }
        }

        @Override
        public String toString() {
            return values.toString();
        }

    }

}
