package org.synek.adventofcode.day8;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CodePair {

    private final String[] signals;
    private final String[] digits;

    public CodePair(String[] signals, String[] digits) {
        this.signals = signals;
        this.digits = digits;
    }

    public int decode() {
        List<Set<Character>> signalCharsList = toCharSets(signals);
        List<Set<Character>> digitCharsList = toCharSets(digits);
        int [] digitToSignalIndex = decodeSignals();

        StringBuilder result = new StringBuilder();
        for (Set<Character> digitChars : digitCharsList) {
            result.append(decodeDigit(digitChars, signalCharsList, digitToSignalIndex));
        }

        return Integer.parseInt(result.toString());
    }

    private int[] decodeSignals() {
        int[] digitToSignalIndex = new int[10];

        int[] remainingIndexesFirstPass = new int[6];
        int j = 0;
        for (int i = 0; i < signals.length; i++) {
            String signal = signals[i];
            int signalLength = signal.length();
            if (signalLength == 2) {
                digitToSignalIndex[1] = i;
            } else if (signalLength == 4) {
                digitToSignalIndex[4] = i;
            } else if (signalLength == 3) {
                digitToSignalIndex[7] = i;
            } else if (signalLength == 7) {
                digitToSignalIndex[8] = i;
            } else {
                remainingIndexesFirstPass[j++] = i;
            }
        }

        Set<Character> one = toCharSet(signals[digitToSignalIndex[1]]);
        Set<Character> four = toCharSet(signals[digitToSignalIndex[4]]);
        Set<Character> seven = toCharSet(signals[digitToSignalIndex[7]]);
        int[] remainingIndexesSecondPass = new int[4];
        j = 0;
        for (int index : remainingIndexesFirstPass) {
            String signal = signals[index];
            int signalLength = signal.length();
            Set<Character> signalChars = toCharSet(signal);
            if (signalLength == 5 && signalChars.containsAll(seven)) {
                digitToSignalIndex[3] = index;
            } else if (signalLength == 6 && signalChars.containsAll(four)) {
                digitToSignalIndex[9] = index;
            } else {
                remainingIndexesSecondPass[j++] = index;
            }
        }

        Set<Character> nine = toCharSet(signals[digitToSignalIndex[9]]);
        for (int index : remainingIndexesSecondPass) {
            String signal = signals[index];
            int signalLength = signal.length();
            Set<Character> signalChars = toCharSet(signal);
            if (signalLength == 5) {
                if (nine.containsAll(signalChars)) {
                    digitToSignalIndex[5] = index;
                } else {
                    digitToSignalIndex[2] = index;
                }
            } else if (signalLength == 6) {
                if (signalChars.containsAll(seven)) {
                    digitToSignalIndex[0] = index;
                } else {
                    digitToSignalIndex[6] = index;
                }
            }
        }

        return digitToSignalIndex;
    }

    private int decodeDigit(Set<Character> digitChars, List<Set<Character>> signalCharsList, int [] digitToSignalIndex) {
        int index = -1;
        for (int i = 0; i < signalCharsList.size(); i++) {
            Set<Character> characterSet = signalCharsList.get(i);
            if (characterSet.equals(digitChars)) {
                index = i;
            }
        }

        for (int i = 0; i < digitToSignalIndex.length; i++) {
            if (digitToSignalIndex[i] == index) {
                return i;
            }
        }
        throw new IllegalStateException("No digit found");
    }


    private List<Set<Character>> toCharSets(String[] input) {
        return Arrays.stream(input)
                .map(this::toCharSet)
                .collect(Collectors.toList());
    }

    private Set<Character> toCharSet(String segment) {
        Set<Character> characterSet = new HashSet<>();
        for (Character c : segment.toCharArray()) {
            characterSet.add(c);
        }
        return characterSet;
    }

    @Override
    public String toString() {
        return String.join(" ", signals)
                + " | "
                + String.join(" ", digits);
    }

    public String digitsToString() {
        return String.join(" ", digits);
    }

    public int digitsOfSegmentsPresent(int digitSegments) {
        return Math.toIntExact(Arrays.stream(digits).filter(digit -> digit.length() == digitSegments)
                .count());
    }
}
