package org.synek.adventofcode.day6;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.synek.adventofcode.util.Util;

public class Day6Challenge {

    private static final String INPUT_RESOURCE = "input";

    private static final int SIMULATION_DAYS = 256;

    public static void main(String[] args) {
        long [] countByDays = new long[9];
        int [] initialDaysToReproduction = readInput(INPUT_RESOURCE);
        for (int index : initialDaysToReproduction) {
            countByDays[index]++;
        }

        for (int i = 0; i < SIMULATION_DAYS; i++) {
            long zeros = countByDays[0];
            countByDays[0] = countByDays[1];
            countByDays[1] = countByDays[2];
            countByDays[2] = countByDays[3];
            countByDays[3] = countByDays[4];
            countByDays[4] = countByDays[5];
            countByDays[5] = countByDays[6];
            countByDays[6] = countByDays[7] + zeros;
            countByDays[7] = countByDays[8];
            countByDays[8] = zeros;
        }

        long sum = 0;
        for (long countByDay : countByDays) {
            sum += countByDay;
        }
        System.out.println("LanternFish population: " + sum);
    }

    private static int [] readInput(String resource) {
        String content;
        try {
            content = Files.readString(Path.of(Util.classpathResourceURI(Day6Challenge.class, resource)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return Arrays.stream(content.split(","))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
