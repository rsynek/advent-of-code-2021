package org.synek.adventofcode.day8;

import static org.synek.adventofcode.day8.CommonDay1Challenge.readCodePairs;

import java.util.List;

public class Day8Challenge2 {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<CodePair> codePairs = readCodePairs(INPUT_RESOURCE);

        long sum = 0;
        for (CodePair codePair : codePairs) {
            int number = codePair.decode();
            sum += number;
            System.out.println(codePair.digitsToString() + ": " + number);
        }

        System.out.println("Sum: " + sum);
    }
}
