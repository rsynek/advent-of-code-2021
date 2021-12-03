package org.synek.adventofcode.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.synek.adventofcode.util.Util;

public class Day3Challenge1 {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        Day3Challenge1 challenge = new Day3Challenge1();

        int[] numbers = challenge.readNumbers(INPUT_RESOURCE);
        int[] oneCount = new int[12];
        for (int i = 0; i < numbers.length; i++) {
            int mask = 0b0000100000000000;
            for (int j = 0; j < oneCount.length; j++) {
                if ((numbers[i] & mask) >= 1) {
                    oneCount[j] = oneCount[j] + 1;
                }
                mask = mask >> 1;
            }
        }
        printIntegerArray(oneCount);

        int halfOfAllNumbers = numbers.length / 2;
        int gamma = 0;
        for (int i = 0; i < oneCount.length; i++) {
            int mostCommon = oneCount[i] > halfOfAllNumbers ? 1 : 0;
            int exponent = oneCount.length - i - 1;
            gamma += mostCommon * Math.pow(2, exponent);
        }
        int mask = 0b0000111111111111;
        int epsilon = (~gamma) & mask;

        System.out.println("Gamma: " + gamma + ", Epsilon: " + epsilon + ", multiplied: " + gamma * epsilon);
    }

    private static void printIntegerArray(int [] array) {
        String arrayString = Arrays.stream(array)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(" | "));
        System.out.println(arrayString);
    }

    private int[] readNumbers(String resource) {
        try {
            return Files.lines(Paths.get(Util.classpathResourceURI(getClass(), resource)))
                    .mapToInt(line -> Integer.parseUnsignedInt(line, 2))
                    .toArray();
        } catch (IOException ioException) {
            throw new IllegalStateException("Reading the input file (" + resource + ") has failed.", ioException);
        }
    }
}
