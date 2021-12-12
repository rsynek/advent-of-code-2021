package org.synek.adventofcode.day12;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.synek.adventofcode.util.Util;

public class Day12Challenge {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        Graph graph = readGraph(INPUT_RESOURCE);

        System.out.println("Paths: " + graph.printAllPaths());
    }

    private static Graph readGraph(String resource) {
        try {
            return new Graph(Files.readAllLines(Path.of(Util.classpathResourceURI(Day12Challenge.class, resource))));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
