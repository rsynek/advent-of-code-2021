package org.synek.adventofcode.day25;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day25Challenge {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        Day25Challenge challenge = readInput(INPUT_RESOURCE);
        int stepWhenNoCucumberMoves = challenge.runSimulation();

        System.out.println(stepWhenNoCucumberMoves);
    }

    private static Day25Challenge readInput(String resource) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Util.classpathResourceURI(Day25Challenge.class, resource)));
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }

        return new Day25Challenge(lines.stream()
                .map(line -> line.toCharArray())
                .toArray(char[][]::new));
    }

    private final char [][] map;

    public Day25Challenge(char[][] map) {
        this.map = map;
    }

    private int runSimulation() {
        int step = 0;
        List<SeaCucumber> canMoveEast = findMovableEastCucumbers();
        List<SeaCucumber> canMoveSouth = null;
        while (!canMoveEast.isEmpty() || !canMoveSouth.isEmpty()) {
            canMoveEast = findMovableEastCucumbers();
            moveCucumbers(canMoveEast);
            canMoveSouth = findMovableSouthCucumbers();
            moveCucumbers(canMoveSouth);
            step++;
        }

        return step;
    }

    private List<SeaCucumber> findMovableEastCucumbers() {
        List<SeaCucumber> movableCucumbers = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = map[i][j];
                if (c == '>') {
                    int column = j == map[0].length - 1 ? 0 : j + 1;
                    if (map[i][column] == '.') {
                        movableCucumbers.add(new SeaCucumber(i, j));
                    }
                }
            }
        }
        return movableCucumbers;
    }

    private List<SeaCucumber> findMovableSouthCucumbers() {
        List<SeaCucumber> movableCucumbers = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                char c = map[i][j];
                if (c == 'v') {
                    int row = i == map.length - 1 ? 0 : i + 1;
                    if (map[row][j] == '.') {
                        movableCucumbers.add(new SeaCucumber(i, j));
                    }
                }
            }
        }
        return movableCucumbers;
    }

    private void moveCucumbers(List<SeaCucumber> seaCucumbers) {
        for (SeaCucumber seaCucumber : seaCucumbers) {
            move(seaCucumber);
        }
    }

    private void move(SeaCucumber seaCucumber) {
        char cucumberDirection = map[seaCucumber.row()][seaCucumber.column()];
        if (cucumberDirection == '>') {
            int column = seaCucumber.column() == map[0].length - 1 ? 0 : seaCucumber.column() + 1;
            map[seaCucumber.row()][column] = '>';
        } else if (cucumberDirection == 'v') {
            int row = seaCucumber.row() == map.length - 1 ? 0 : seaCucumber.row() + 1;
            map[row][seaCucumber.column()] = 'v';
        } else {
            throw new IllegalArgumentException("Provided character (" + cucumberDirection + ") does not represent a sea cucumber direction.");
        }
        map[seaCucumber.row()][seaCucumber.column()] = '.';
    }

    private record SeaCucumber(int row, int column) {

    }
}
