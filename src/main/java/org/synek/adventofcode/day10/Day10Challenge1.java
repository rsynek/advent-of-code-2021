package org.synek.adventofcode.day10;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day10Challenge1 {
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<String> lines = readLines(INPUT_RESOURCE);
        long score = 0;
        for (String line : lines) {
            score += parseLine(line);
        }

        System.out.println("Score: " + score);
    }

    private static int parseLine(String line) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : line.toCharArray()) {
            if (isOpeningBracket(c)) {
                stack.push(c);
            } else {
                char openingBracket = stack.pop();
                if (!areMatchingBrackets(openingBracket, c)) {
                    return scoreBracket(c);
                }
            }
        }
        return 0;
    }

    private static boolean isOpeningBracket(char c) {
        return c == '(' || c == '[' || c == '{' || c == '<';
    }

    private static boolean areMatchingBrackets(char openingBracket, char closingBracket) {
        return (openingBracket == '(' && closingBracket == ')')
                || (openingBracket == '[' && closingBracket == ']')
                || (openingBracket == '{' && closingBracket == '}')
                || (openingBracket == '<' && closingBracket == '>');

    }

    private static int scoreBracket(char c) {
        return switch (c) {
            case ')' -> 3;
            case ']' -> 57;
            case '}' -> 1197;
            case '>' -> 25137;
            default -> throw new IllegalArgumentException("Unknown character (" + c + ")");
        };
    }

    private static List<String> readLines(String resource) {
        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day10Challenge1.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
