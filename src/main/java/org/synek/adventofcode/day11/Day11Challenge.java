package org.synek.adventofcode.day11;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.synek.adventofcode.util.Util;

public class Day11Challenge {

    private static final String INPUT_RESOURCE = "input";
    private static final int STEPS = 100;

    public static void main(String[] args) {
        int[][] octopuses = readInput(INPUT_RESOURCE);

        print(octopuses);

        long numberOfFlashes = 0;
        int stepWhenAllFlash = -1;
        long numberOfFlashesPerStep = 0;
        for (int i = 0; i < STEPS; i++) {

            Set<Point> octopusesFlash = new HashSet<>();
            for (int row = 0; row < octopuses.length; row++) {
                for (int column = 0; column < octopuses[row].length; column++) {
                    octopuses[row][column]++;
                    int energy = octopuses[row][column];
                    if (energy > 9) {
                        octopusesFlash.add(new Point(row, column));
                    }
                }
            }

            while (!octopusesFlash.isEmpty()) {
                Point octopus = octopusesFlash.iterator().next();
                octopusesFlash.remove(octopus);
                numberOfFlashes++;
                numberOfFlashesPerStep++;
                octopusesFlash.addAll(flash(octopus, octopuses));
            }

            if (numberOfFlashesPerStep == 100) {
                stepWhenAllFlash = i + 1;
                print(octopuses);
                break;
            }

        }

        System.out.println("Flashes: " + numberOfFlashes + " all octopuses flash in step: " + stepWhenAllFlash);
    }

    private static void print(int[][] octopuses) {
        for (int row = 0; row < octopuses.length; row++) {
            for (int column = 0; column < octopuses[row].length; column++) {
                System.out.print(octopuses[row][column]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    private static List<Point> flash(Point coordinates, int[][] octopuses) {
        List<Point> octopusesToFlash = new ArrayList<>();
        int row = coordinates.row();
        int column = coordinates.column();
        octopuses[row][column] = 0;
        Point[] neighbours = {
                new Point(row - 1, column - 1),
                new Point(row - 1, column),
                new Point(row - 1, column + 1),
                new Point(row, column - 1),
                new Point(row, column + 1),
                new Point(row + 1, column - 1),
                new Point(row + 1, column),
                new Point(row + 1, column + 1),
        };


        for (Point neighbour : neighbours) {
            if (neighbour.row() < 0 || neighbour.row() >= octopuses.length
                    || neighbour.column() < 0 || neighbour.column() >= octopuses.length) {
                continue;
            }
            if (octopuses[neighbour.row()][neighbour.column()] > 0) { // this one has not flashed this step.
                octopuses[neighbour.row()][neighbour.column()]++; // Increase the energy from the flash.
            }
            if (octopuses[neighbour.row()][neighbour.column()] > 9) {
                octopusesToFlash.add(neighbour);
            }
        }
        return octopusesToFlash;
    }

    private static int[][] readInput(String resource) {
        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day11Challenge.class, resource)))
                    .stream()
                    .map(s -> s.codePoints()
                            .mapToObj(c -> String.valueOf((char) c))
                            .mapToInt(Integer::parseInt)
                            .toArray()
                    )
                    .toArray(int[][]::new);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
