package org.synek.adventofcode.day22;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day22Challenge {
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<Instruction> instructions = readInstructions(INPUT_RESOURCE);

        instructions.forEach(System.out::println);

        Naive naive = new Naive();
        long count = naive.processInstructions(instructions);
        System.out.println(count);
    }

    private static List<Instruction> readInstructions(String resource) {
        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day22Challenge.class, resource))).stream()
                    .map(line -> {
                        String[] actionAndRanges = line.split(" ");
                        boolean on = "on".equals(actionAndRanges[0]) ? true : false;
                        String[] ranges = actionAndRanges[1].split(",");
                        int[] coordinates = new int[6];
                        int i = 0;
                        for (String range : ranges) {
                            String[] coordinatesString = range.split("=")[1].split("\\.\\.");
                            coordinates[i++] = Integer.parseInt(coordinatesString[0]);
                            coordinates[i++] = Integer.parseInt(coordinatesString[1]);
                        }
                        return new Instruction(on, coordinates[0], coordinates[1], coordinates[2], coordinates[3],
                                coordinates[4], coordinates[5]);
                    })
                    .toList();

        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    private static class Naive {
        private final boolean [][][] space = new boolean[101][101][101];

        public long processInstructions(List<Instruction> instructions) {
            for (Instruction instruction : instructions) {
                if (isWithinBoundaries(instruction)) {
                    Instruction normalizedInstruction = new Instruction(instruction.on(), 
                            instruction.x1() + 50, 
                            instruction.x2() + 50, 
                            instruction.y1() + 50, 
                            instruction.y2() + 50, 
                            instruction.z1() + 50,
                            instruction.z2() + 50);
                    for (int i = normalizedInstruction.x1(); i <= normalizedInstruction.x2(); i++) {
                        for (int j = normalizedInstruction.y1(); j <= normalizedInstruction.y2(); j++) {
                            for (int k = normalizedInstruction.z1(); k <= normalizedInstruction.z2(); k++) {
                                space[i][j][k] = normalizedInstruction.on;
                            }
                        }
                    }
                }
            }
            return countCubes();
        }

        private boolean isWithinBoundaries(Instruction instruction) {
            return isWithinBoundaries(instruction.x1()) && isWithinBoundaries(instruction.x2())
                    && isWithinBoundaries(instruction.y1()) && isWithinBoundaries(instruction.y2())
                    && isWithinBoundaries(instruction.z1()) && isWithinBoundaries(instruction.z2());
        }

        private boolean isWithinBoundaries(int number) {
            return number >= -50 && number <= 50;
        }

        private long countCubes() {
            long sum = 0;
            for (int i = 0; i < space.length; i++) {
                for (int j = 0; j < space[0].length; j++) {
                    for (int k = 0; k < space[0][0].length; k++) {
                        if (space[i][j][k])
                            sum++;
                    }
                }
            }
            return sum;
        }
    }

    private record Instruction(boolean on, int x1, int x2, int y1, int y2, int z1, int z2) {

    }
}
