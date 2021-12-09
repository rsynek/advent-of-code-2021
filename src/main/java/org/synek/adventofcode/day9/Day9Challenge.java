package org.synek.adventofcode.day9;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import org.synek.adventofcode.util.Util;

public class Day9Challenge {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        int[][] heightMap = readInput(INPUT_RESOURCE);

        int[] greatestThreeBasins = new int[3];
        long sumOfRisks = 0;
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap[i].length; j++) {
                int currentPoint = heightMap[i][j];
                boolean upIsHigher = i <= 0 || heightMap[i - 1][j] > currentPoint;
                boolean downIsHigher = i >= heightMap.length - 1 || heightMap[i + 1][j] > currentPoint;
                boolean leftIsHigher = j <= 0 || heightMap[i][j - 1] > currentPoint;
                boolean rightIsHigher = j >= heightMap[i].length - 1 || heightMap[i][j + 1] > currentPoint;
                if (upIsHigher && downIsHigher && leftIsHigher && rightIsHigher) {
                    System.out.println(String.format("Lowest point (%d,%d) found with height %d", i, j, currentPoint));
                    sumOfRisks += currentPoint + 1;
                    int basinSize = computeBasinSize(heightMap, i, j);
                    System.out.println("Basin size: " + basinSize);
                    int minBasinIndex = 0;
                    int minBasin = greatestThreeBasins[minBasinIndex];
                    for (int k = 1; k < greatestThreeBasins.length; k++) {
                        if (greatestThreeBasins[k] < minBasin) {
                            minBasinIndex = k;
                            minBasin = greatestThreeBasins[minBasinIndex];
                        }
                    }
                    if (basinSize > minBasin) {
                        greatestThreeBasins[minBasinIndex] = basinSize;
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Risks: " + sumOfRisks);
        System.out.println("Basins: " + (greatestThreeBasins[0] * greatestThreeBasins[1] * greatestThreeBasins[2]));
    }

    private static int computeBasinSize(int[][] heightMap, int lowestPointRow, int lowestPointColumn) {
        int size = 0;
        Point lowestPoint = new Point(lowestPointRow, lowestPointColumn);
        int maxRow = heightMap.length - 1;
        int maxColumn = heightMap[0].length - 1;
        Set<Point> alreadyProcessed = new HashSet<>();
        Set<Point> toProcess = new HashSet<>();
        toProcess.add(lowestPoint);

        while (!toProcess.isEmpty()) {
            Point point = toProcess.iterator().next();
            if (heightMap[point.row()][point.column()] < 9) {
                size++;
                alreadyProcessed.add(point);
                Set<Point> neighbourhoodPoints = neighbourhood(point, maxRow, maxColumn);
                neighbourhoodPoints.removeAll(alreadyProcessed);
                toProcess.addAll(neighbourhoodPoints);
            }
            toProcess.remove(point);
        }

        return size;
    }

    private static Set<Point> neighbourhood(Point point, int maxRow, int maxColumn) {
        Set<Point> neighbourhood = new HashSet<>();
        if (point.row() > 0) {
            neighbourhood.add(new Point(point.row() - 1, point.column()));
        }
        if (point.column() > 0) {
            neighbourhood.add(new Point(point.row(), point.column() - 1));
        }
        if (point.row() < maxRow) {
            neighbourhood.add(new Point(point.row() + 1, point.column()));
        }
        if (point.column() < maxColumn) {
            neighbourhood.add(new Point(point.row(), point.column() + 1));
        }

        return neighbourhood;
    }

    private static int[][] readInput(String resource) {

        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day9Challenge.class, resource)))
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
