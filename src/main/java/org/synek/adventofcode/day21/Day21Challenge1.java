package org.synek.adventofcode.day21;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day21Challenge1 {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        Game game = readInput(INPUT_RESOURCE);
        System.out.println("Score: " + game.play());
    }

    private static Game readInput(String resource) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Util.classpathResourceURI(Day21Challenge1.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        String firstPlayerInput = lines.get(0);
        String secondPlayerInput = lines.get(1);

        return new Game(parseStartingPosition(firstPlayerInput), parseStartingPosition(secondPlayerInput));
    }

    private static int parseStartingPosition(String line) {
        return Integer.parseInt(line.split(": ")[1]);
    }
}
