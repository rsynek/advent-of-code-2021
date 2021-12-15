package org.synek.adventofcode.day15;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day15Challenge {
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        int[][] riskMap = readInput(INPUT_RESOURCE);
        int[][] biggerRiskMap = extrapolateBiggerMap(riskMap);

        Graph graph = new Graph(biggerRiskMap);
        long riskCost = graph.shortestPath();
        System.out.println(riskCost);
    }

    private static int[][] readInput(String resource) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Util.classpathResourceURI(Day15Challenge.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        return lines.stream()
                .map(line -> line.codePoints()
                        .map(digit -> digit - '0')
                        .toArray()
                )
                .toArray(int[][]::new);
    }

    private static int[][] extrapolateBiggerMap(int [][] riskMap) {
        int [][] biggerRiskMap = new int[riskMap.length * 5][riskMap[0].length * 5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                copyAndIncrease(riskMap, biggerRiskMap, i, j,i + j);
            }
        }

        return biggerRiskMap;
    }

    private static void copyAndIncrease(int[][] source, int[][] destination, int startRow, int startColumn, int increase) {
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < source[i].length; j++) {
                int value = (source[i][j] + increase);
                while (value > 9) {
                    value = value - 9;
                }
                destination[startRow * source.length + i][startColumn * source[i].length + j] = value;
            }
        }
    }
}
