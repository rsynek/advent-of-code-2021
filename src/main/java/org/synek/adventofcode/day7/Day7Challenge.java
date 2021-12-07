package org.synek.adventofcode.day7;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.synek.adventofcode.util.Util;

public class Day7Challenge {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        int[] crabs = readInput(INPUT_RESOURCE);

        int max = crabs[0];
        int min = crabs[0];
        for (int i = 1; i < crabs.length; i++) {
            if (crabs[i] > max) {
                max = crabs[i];
            }
            if (crabs[i] < min) {
                min = crabs[i];
            }
        }

        int minPosition = min;
        int minSumOfDistances = 0;
        for (int i = min; i <= max; i++) {
            int sumOfDistances = 0;
            for (int j = 0; j < crabs.length; j++) {
                sumOfDistances += countDistance(Math.abs(crabs[j] - i));
            }
            if (i == min || sumOfDistances < minSumOfDistances) {
                minSumOfDistances = sumOfDistances;
                minPosition = i;
            }
        }

        System.out.println("Position: " + minPosition + " Fuel:" + minSumOfDistances);
    }

    private static int countDistance(int steps) {
        int sum = 0;
        for (int i = 1; i <= steps; i++) {
            sum += i;
        }
        return sum;
    }

    private static int[] readInput(String resource) {
        String content;
        try {
            content = Files.readString(Path.of(Util.classpathResourceURI(Day7Challenge.class, resource)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return Arrays.stream(content.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
