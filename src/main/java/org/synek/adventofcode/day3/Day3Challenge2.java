package org.synek.adventofcode.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import org.synek.adventofcode.util.Util;

public class Day3Challenge2 {
    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        int[] numbers = readNumbers(INPUT_RESOURCE);
        int oxygenGenerator = getRating(numbers, (oneCount, zeroCount) -> oneCount > zeroCount || (oneCount >= zeroCount));
        int scrubber = getRating(numbers, (oneCount, zeroCount) -> oneCount <= zeroCount && (oneCount < zeroCount));
        System.out.println("Oxygen generator rating:"  + oxygenGenerator);
        System.out.println("CO2 scrubber rating:"  + scrubber);
        System.out.println("Result: " + oxygenGenerator * scrubber);
    }

    private static int getRating(int[] numbers, BiPredicate<Integer, Integer> comparison) {
        List<Integer> remainingNumbers = Arrays.stream(numbers).boxed().collect(Collectors.toList());
        int[] bitIndex = { 0 };
        while (remainingNumbers.size() > 1) {
            boolean commonBit = commonBit(remainingNumbers, bitIndex[0], comparison);
            remainingNumbers = remainingNumbers.stream()
                    .filter(integer -> checkBit(integer, bitIndex[0]) == commonBit)
                    .collect(Collectors.toList());
            bitIndex[0] = bitIndex[0] + 1;
        }
        return remainingNumbers.get(0);
    }

    private static boolean commonBit(List<Integer> numbers, int bitIndex, BiPredicate<Integer, Integer> comparison) {
        int oneCount = 0;
        int length = numbers.size();
        final int mask = 0b0000100000000000 >> bitIndex;
        for (Integer number : numbers) {
            if ((number & mask) >= 1) {
                oneCount++;
            }
        }
        int zeroCount = length - oneCount;
        return comparison.test(oneCount, zeroCount);
    }

    private static boolean checkBit(int number, int bitIndex) {
        return (number & (0b100000000000 >> bitIndex)) > 0;
    }

    private static int[] readNumbers(String resource) {
        try {
            return Files.lines(Paths.get(Util.classpathResourceURI(Day3Challenge2.class, resource)))
                    .mapToInt(line -> Integer.parseUnsignedInt(line, 2))
                    .toArray();
        } catch (IOException ioException) {
            throw new IllegalStateException("Reading the input file (" + resource + ") has failed.", ioException);
        }
    }
}
