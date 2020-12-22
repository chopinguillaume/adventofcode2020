package fr.gchopin.advent2020.days;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.gchopin.advent2020.Day;

public class Day14 extends Day {

    public Day14(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<String> lines = _input.getLines();

        String currentMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        Map<Integer, Long> memory = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(" = ");
            if (split[0].equals("mask")) {
                currentMask = split[1];
                continue;
            } else if (split[0].startsWith("mem")) {
                // Get the value between [x]
                String memorySlotStr = split[0].split("[\\[\\]]")[1];
                int memorySlot = Integer.parseInt(memorySlotStr);

                int decimalValue = Integer.parseInt(split[1]);
                String binaryString = Integer.toBinaryString(decimalValue);
                binaryString = String.format("%36s", binaryString).replace(' ', '0'); // Zero Padding

                String binaryAfterMask = applyMask(currentMask, binaryString, 'X');
                long valueAfterMask = Long.parseLong(binaryAfterMask, 2);

                memory.put(memorySlot, valueAfterMask);
            }
        }

        Long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println(sum);

    }

    String applyMask(String mask, String binaryValue, Character doNothingOnThisMaskChar) {
        if (mask.length() != binaryValue.length()) {
            throw new IllegalStateException();
        }

        StringBuilder binaryDuringMask = new StringBuilder();

        for (int i = 0; i < mask.length(); i++) {
            Character maskChar = mask.charAt(i);
            Character valueChar = binaryValue.charAt(i);

            char newChar;
            if (maskChar.equals(doNothingOnThisMaskChar)) {
                newChar = valueChar;
            } else {
                newChar = maskChar;
            }
            binaryDuringMask.append(newChar);
        }
        return binaryDuringMask.toString();
    }

    @Override
    public void part2() {
        List<String> lines = _input.getLines();

        String currentMask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
        Map<Long, Long> memory = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(" = ");
            if (split[0].equals("mask")) {
                currentMask = split[1];
                continue;
            } else if (split[0].startsWith("mem")) {
                // Get the value between [x]
                String memorySlotStr = split[0].split("[\\[\\]]")[1];
                int memorySlot = Integer.parseInt(memorySlotStr);
                String binaryMemory = Integer.toBinaryString(memorySlot);
                binaryMemory = String.format("%36s", binaryMemory).replace(' ', '0'); // Zero Padding
                String binaryAfterMask = applyMask(currentMask, binaryMemory, '0');

                List<Long> memorySlots = getAllMemorySlots(binaryAfterMask);

                long decimalValue = Long.parseLong(split[1]);

                for (Long slot : memorySlots) {
                    memory.put(slot, decimalValue);
                }
            }
        }

        Long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println(sum);
    }

    private List<Long> getAllMemorySlots(String binaryString) {
        List<Long> allSlots = new ArrayList<>();

        List<String> allBinaries = getAllBinaryStrings(binaryString);
        for (String binary : allBinaries) {
            allSlots.add(Long.parseLong(binary, 2));
        }

        return allSlots;
    }

    private List<String> getAllBinaryStrings(String string) {
        List<String> allStrings = new ArrayList<>();

        // Iterate over the characters
        // When 'X', recursive search the rest and prepend 0 and 1 to the children
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == 'X') {
                if (i == string.length() - 1) {
                    // Last character of string, no possible children
                    allStrings.add(string.substring(0, i) + "0");
                    allStrings.add(string.substring(0, i) + "1");
                } else {
                    // Recursive call on the rest of the string to get all children
                    List<String> children = getAllBinaryStrings(string.substring(i + 1));
                    for (String child : children) {
                        allStrings.add(string.substring(0, i) + "0" + child);
                        allStrings.add(string.substring(0, i) + "1" + child);
                    }
                }
                return allStrings;
            }
        }

        // if 'X' is never found, the only possible value is the string as given
        allStrings.add(string);
        return allStrings;
    };

}
