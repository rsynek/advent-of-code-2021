package org.synek.adventofcode.day18;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import org.synek.adventofcode.util.Util;

public class Day18Challenge {

    private static final String INPUT_RESOURCE = "input";

    public static void main(String[] args) {
        List<String> numberStringList = readInput(INPUT_RESOURCE);
        List<Number> inputNumbers = numberStringList.stream()
                .map(Number::parse)
                .toList();

        Iterator<Number> inputNumbersIterator = inputNumbers.iterator();
        Number result = inputNumbersIterator.next();
        while (inputNumbersIterator.hasNext()) {
            Number next = inputNumbersIterator.next();
            result = result.add(next);
        }

        System.out.println("Magnitude:" + result.magnitude());

        long maxMagnitude = 0;
        for (String numberStringA : numberStringList) {
            for (String numberStringB : numberStringList) {
                Number numberA = Number.parse(numberStringA);
                Number numberB = Number.parse(numberStringB);
                if (numberA != numberB) { // pick only distinct pairs
                    Number sum = numberA.add(numberB);
                    long magnitude = sum.magnitude();
                    if (magnitude > maxMagnitude) {
                        maxMagnitude = magnitude;
                    }
                }
            }
        }

        System.out.println("Max magnitude of any pair:" + maxMagnitude);
    }

    // [[[5,[2,8]],4],[5,[[9,9],0]]]
        /*
                [A, B]

             A = [C,4]
             C = [5,D]
             D = [2,8]

             B = [5,E]
             E = [F,0]
             F = [9,9]

             [[[ => 3x create & push new pair
             5 => value; peek last pair, set its left valueOrPair

             [ => create new pair, comma before => peek last pair, set its right valueOrPair. Push the new pair
             2 => value; peek last pair, set its left valueOrPair
             8 => value; peek last pair, comma before => set its right valueOrPair
             ] => pop last pair; peek the last pair and set it as the parent
             ] => pop last pair; peek the last pair and set it as the parent
             4 => value; peek last pair, comma before => set its right valueOrPair
             ] => pop last pair; peek the last pair and set it as the parent
             [ => create new pair, comma before => peek the last pair, set its right valueOrPair. Push the new pair
             5 => value, peek last pair, set its left valueOrPair
             [ => create new pair, comma before => peek the last pair, set its right valueOrPair. Push the new pair
             [ => create new pair, peek the last pair, set its left valueOrPair. Push the new pair
             9 => value; peek last pair, set its left valueOrPair
             9 => value; peek last pair, comma before => set its right valueOrPair
             ] => pop last pair; peek the last pair and set it as the parent
             0 => value; peek last pair, comma before => set its right valueOrPair
             ]]] => pop 3 pairs; peek the last pair and set it as the parent

             At the same time: keep the list of Values; with every new value, set the new value's left to the last value
             and set the last value's right to the new value

        */
    private static List<String> readInput(String resource) {
        try {
            return Files.readAllLines(Path.of(Util.classpathResourceURI(Day18Challenge.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }
}
