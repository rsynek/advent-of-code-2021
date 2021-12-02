package org.synek.adventofcode.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.synek.adventofcode.util.Util;

public class Day2Challenge1 {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        Day2Challenge1 challenge = new Day2Challenge1();
        List<AbstractMove> moves = challenge.readMoves(INPUT_RESOURCE);
        Location location = new Location(0, 0, 0);
        for (AbstractMove move : moves) {
            location = move.nextLocation(location);
        }
        System.out.println(location);
    }

    private List<AbstractMove> readMoves(String resource) {
        try {
            return Files.lines(Paths.get(Util.classpathResourceURI(getClass(), resource)))
                    .map(this::parseMove)
                    .collect(Collectors.toList());
        } catch (IOException ioException) {
            throw new IllegalStateException("Reading the input file (" + resource + ") has failed.", ioException);
        }
    }

    private AbstractMove parseMove(String line) {
        String[] tokens = line.split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("The line (" + line + " cannot be parsed.");
        }

        int distance = Integer.parseInt(tokens[1]);

        return switch (tokens[0]) {
            case "forward" -> new Forward(distance);
            case "up" -> new Up(distance);
            case "down" -> new Down(distance);
            default -> throw new IllegalArgumentException("Unknown direction (" + tokens[0] + ").");
        };
    }
}
