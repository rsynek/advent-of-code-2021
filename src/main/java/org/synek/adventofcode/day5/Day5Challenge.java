package org.synek.adventofcode.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.synek.adventofcode.util.Util;

public class Day5Challenge {
    private static final int MATRIX_SIZE = 991;
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        int count = 0;
        int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (String entry : readInput(INPUT_RESOURCE)) {
            Line line = parseLine(entry);
            int minX = Math.min(line.startX, line.endX);
            int maxX = Math.max(line.startX, line.endX);
            int minY = Math.min(line.startY, line.endY);
            int maxY = Math.max(line.startY, line.endY);

            if (line.isHorizontal() || line.isVertical()) {
                for (int i = minY; i <= maxY; i++) {
                    for (int j = minX; j <= maxX; j++) {
                        if (matrix[i][j]++ == 1) {
                            count++;
                        }
                    }
                }
            } else if (line.isDiagonal()) {
                if (line.isRaisingDiagonal()) {
                    for (int i = 0; i <= maxX - minX; i++) {
                        if (matrix[minY + i][minX + i]++ == 1) {
                            count++;
                        }
                    }
                } else {
                    for (int i = 0; i <= maxX - minX; i++) {
                        if (matrix[maxY - i][minX + i]++ == 1) {
                            count++;
                        }
                    }
                }

            }
        }

        System.out.println("Count of crossings of 2 or more lines: " + count);
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------------------");
    }

    private static Line parseLine(String line) {
        String[] points = line.split(" -> ");
        String[] start = points[0].split(",");
        String[] end = points[1].split(",");
        return new Line(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(end[0]), Integer.parseInt(end[1]));
    }

    private static String[] readInput(String resource) {
        try {
            return Files.lines(Paths.get(Util.classpathResourceURI(Day5Challenge.class, resource)))
                    .toArray(String[]::new);
        } catch (IOException ioException) {
            throw new IllegalStateException("Reading the input file (" + resource + ") has failed.", ioException);
        }
    }
}
