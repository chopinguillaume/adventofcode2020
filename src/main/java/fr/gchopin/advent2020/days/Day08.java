package fr.gchopin.advent2020.days;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.gchopin.advent2020.Day;

public class Day08 extends Day {

    public Day08(String inputFile) {
        super(inputFile);
    }

    @Override
    public void part1() {
        List<String> instructions = _input.getLines();

        ExitValue exitValue = runInstructions(instructions);
        System.out.println(exitValue.accumulator);
    }

    /**
     * Runs the instructions until it loops on an already visited instruction.
     */
    private ExitValue runInstructions(List<String> instructions) {
        int accumulator = 0;
        Set<Integer> alreadyExecutedLines = new HashSet<>();

        int currentInstruction = 0;

        while (true) {
            if (currentInstruction >= instructions.size()) {
                // Program terminates
                return new ExitValue(false, accumulator);
            }

            String instruction = instructions.get(currentInstruction);

            if (alreadyExecutedLines.contains(currentInstruction)) {
                // Starts looping
                return new ExitValue(true, accumulator);
            } else {
                alreadyExecutedLines.add(currentInstruction);
            }

            String[] instructionSplit = instruction.split(" ");
            String operation = instructionSplit[0];
            Integer value = Integer.parseInt(instructionSplit[1]);

            switch (operation) {
                case "acc":
                    accumulator += value;
                    currentInstruction++;
                    break;
                case "jmp":
                    currentInstruction += value;
                    break;
                case "nop":
                    currentInstruction++;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void part2() {
        List<String> instructions = _input.getLines();

        for (int idxChange = 0; idxChange < instructions.size(); idxChange++) {

            List<String> instructionsChanged = new ArrayList<>();
            instructionsChanged.addAll(instructions);

            String instr = instructions.get(idxChange);
            if (instr.startsWith("nop")) {
                instr = instr.replace("nop", "jmp");
                instructionsChanged.set(idxChange, instr);
            } else if (instr.startsWith("jmp")) {
                instr = instr.replace("jmp", "nop");
                instructionsChanged.set(idxChange, instr);
            } else {
                continue;
            }

            ExitValue exitValue = runInstructions(instructionsChanged);

            if (!exitValue.isLooping) {
                System.out.println(exitValue.accumulator);
                return;
            }
        }

    }

    private class ExitValue {
        boolean isLooping;
        Integer accumulator;

        public ExitValue(boolean isLooping, Integer accumulator) {
            this.isLooping = isLooping;
            this.accumulator = accumulator;
        }

        @Override
        public String toString() {
            if (isLooping) {
                return "Loops with acc=" + accumulator;
            }

            return "Terminates with acc=" + accumulator;
        }
    }

}
