package org.synek.adventofcode.day10;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day10Challenge2 {
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<String> lines = readLines(INPUT_RESOURCE);

        List<Long> scores = new ArrayList<>();
        for (String line : lines) {
            long score = parseLine(line);
            if (score > 0) {
                scores.add(score);
                System.out.println(score);
            }
        }

        Collections.sort(scores);
        long middleScore = scores.get(scores.size() / 2);
        System.out.println("Score: " + middleScore);
    }

    private static long parseLine(String line) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : line.toCharArray()) {
            if (isOpeningBracket(c)) {
                stack.push(c);
            } else {
                char openingBracket = stack.pop();
                if (!areMatchingBrackets(openingBracket, c)) {
                    return 0;
                }
            }
        }
        long score = 0;
        while (!stack.isEmpty()) {
            char missingChar = stack.pop();
            score = score * 5 + scoreBracket(missingChar);
        }
        return score;
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
            case '(' -> 1;
            case '[' -> 2;
            case '{' -> 3;
            case '<' -> 4;
            default -> throw new IllegalArgumentException("Unknown character (" + c + ")");
        };
    }

    private static List<String> readLines(String resource) {
        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day10Challenge2.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
