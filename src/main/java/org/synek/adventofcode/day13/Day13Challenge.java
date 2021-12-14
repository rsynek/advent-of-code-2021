package org.synek.adventofcode.day13;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day13Challenge {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        Day13Challenge day13Challenge = fromInput(INPUT_RESOURCE);

        day13Challenge.foldAll();
        //day13Challenge.foldFirst();
        System.out.println("Visible: " + day13Challenge.countTrue());
        day13Challenge.print();
    }

    private static Day13Challenge fromInput(String resource) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Util.classpathResourceURI(Day13Challenge.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        List<Point> points = new ArrayList<>();
        List<Fold> folds = new ArrayList<>();
        boolean readCoordinates = true;
        for (String line : lines) {
            if (line.isEmpty()) {
                readCoordinates = false;
            } else if (readCoordinates) {
                String[] coordinates = line.split(",");
                points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
            } else { // read folding
                String[] foldCommand = line.split("\\s+");
                String[] fold = foldCommand[2].split("=");
                int location = Integer.parseInt(fold[1]);
                folds.add(new Fold(fold[0], location));
            }
        }

        int maxX = 0;
        int maxY = 0;
        for (Point point : points) {
            if (point.x > maxX) {
                maxX = point.x;
            }
            if (point.y > maxY) {
                maxY = point.y;
            }
        }

        boolean[][] coordinates = new boolean[maxY + 1][maxX + 1];
        for (Point point : points) {
            coordinates[point.y][point.x] = true;
        }

        return new Day13Challenge(coordinates, folds);
    }

    private boolean[][] coordinates;
    private List<Fold> foldList;

    public Day13Challenge() {
    }

    public Day13Challenge(boolean[][] coordinates, List<Fold> foldList) {
        this.coordinates = coordinates;
        this.foldList = foldList;
    }

    public void foldFirst() {
        Fold fold = foldList.get(0);
        fold(fold);
    }

    public void foldAll() {
        for (Fold fold : foldList) {
            fold(fold);
        }
    }

    protected void fold(Fold fold) {
        System.out.println("Folding along " + fold.axis() + " at " + fold.location());
        if (fold.axis.equals("y")) {
            this.coordinates = foldUp(coordinates, fold.location());
        } else {
            this.coordinates = foldLeft(coordinates, fold.location());
        }
//        print();
//        System.out.println("*********************************************************************");
//        System.out.println("*********************************************************************");
//        System.out.println("*********************************************************************");
//        System.out.println("*********************************************************************");
    }

    protected boolean[][] foldUp(boolean[][] originalSheet, int x) {
        int rows = Math.max(originalSheet.length - x - 1, x);
        int columns = originalSheet[0].length;
        boolean[][] foldedSheet = new boolean[rows][columns];


        int beforeIndex = x;
        int afterIndex = x;
        int i = rows - 1;
        while (i >= 0) {
            beforeIndex--;
            afterIndex++;
            if (beforeIndex < 0) {
                foldedSheet[i] = Arrays.copyOf(originalSheet[afterIndex], columns);
            } else if (afterIndex >= originalSheet.length) {
                foldedSheet[i] = Arrays.copyOf(originalSheet[beforeIndex], columns);
            } else {
                for (int j = 0; j < columns; j++) {
                    foldedSheet[i][j] = originalSheet[beforeIndex][j] || originalSheet[afterIndex][j];
                }
            }
            i--;

        }

        return foldedSheet;
    }

    protected boolean[][] foldLeft(boolean[][] originalSheet, int y) {
        int rows = originalSheet.length;
        int columns = Math.max(originalSheet[0].length - y - 1, y);
        boolean[][] foldedSheet = new boolean[rows][columns];


        int beforeIndex = y;
        int afterIndex = y;
        int i = columns - 1;
        while (i >= 0) {
            beforeIndex--;
            afterIndex++;
            if (beforeIndex < 0) {
                for (int j = 0; j < rows; j++) {
                    foldedSheet[j][i] = originalSheet[j][afterIndex];
                }
            } else if (afterIndex >= originalSheet[0].length) {
                for (int j = 0; j < rows; j++) {
                    foldedSheet[j][i] = originalSheet[j][beforeIndex];
                }
            } else {
                for (int j = 0; j < rows; j++) {
                    foldedSheet[j][i] = originalSheet[j][beforeIndex] || originalSheet[j][afterIndex];
                }
            }
            i--;
        }

        return foldedSheet;
    }

    public int countTrue() {
        int count = 0;
        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                if (coordinates[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public void print() {
        for (int i = 0; i < coordinates.length; i++) {
            for (int j = 0; j < coordinates[i].length; j++) {
                char c = coordinates[i][j] ? '#' : '.';
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private record Point(int x, int y) {

    }

    private record Fold(String axis, int location) {
    }
}
