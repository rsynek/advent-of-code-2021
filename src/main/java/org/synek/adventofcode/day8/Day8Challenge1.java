package org.synek.adventofcode.day8;

import static org.synek.adventofcode.day8.CommonDay1Challenge.readCodePairs;

import java.util.List;

public class Day8Challenge1 {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<CodePair> codePairs = readCodePairs(INPUT_RESOURCE);

        codePairs.forEach(System.out::println);

        int sumOfDigits = 0;
        int[] digitSegmentsLengths = {2, 3, 4, 7};
        for (CodePair codePair : codePairs) {
            for (int digitSegmentsLength : digitSegmentsLengths) {
                sumOfDigits += codePair.digitsOfSegmentsPresent(digitSegmentsLength);
            }
        }

        System.out.println("How many times do digits 1, 4, 7, or 8 appear: " + sumOfDigits);
    }
}

