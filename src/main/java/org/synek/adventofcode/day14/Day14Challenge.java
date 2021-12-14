package org.synek.adventofcode.day14;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.synek.adventofcode.util.Util;

public class Day14Challenge {

    private static final String INPUT_RESOURCE = "input";
    private static final int STEPS = 40;

    public static void main(String[] args) {
        Day14Challenge challenge = fromInput(INPUT_RESOURCE);
        challenge.applyReplacements();
    }

    private static Day14Challenge fromInput(String resource) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Util.classpathResourceURI(Day14Challenge.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        Rule[] rules = new Rule[lines.size() - 2];
        Iterator<String> lineIterator = lines.iterator();
        String inputString = lineIterator.next();
        lineIterator.next(); // empty line
        int i = 0;
        while (lineIterator.hasNext()) {
            String[] ruleLine = lineIterator.next().split(" -> ");
            String from = ruleLine[0];
            char[] fromChars = from.toCharArray();
            Rule rule = new Rule(fromChars[0], fromChars[1], ruleLine[1].toCharArray()[0]);
            rules[i++] = rule;
        }

        return new Day14Challenge(inputString, rules);
    }

    private final Map<String, Rule> ruleMapping = new HashMap<>();
    private final Rule[] rules;
    private final List<Rule> ruleList = new ArrayList<>();

    public Day14Challenge(String inputString, Rule[] rules) {
        this.rules = rules;
        buildRuleNetwork();
        buildInitialRules(inputString);
    }

    private void buildRuleNetwork() {
        for (Rule rule : rules) {
            String leftPart = String.valueOf(rule.getLeft()) + rule.getRight();
            ruleMapping.put(leftPart, rule);
        }

        for (Rule rule : rules) {
            String left = String.valueOf(rule.getLeft()) + rule.getMiddle();
            String right = String.valueOf(rule.getMiddle()) + rule.getRight();
            Rule leftRule = ruleMapping.get(left);
            rule.setLeftRule(leftRule);
            Rule rightRule = ruleMapping.get(right);
            rule.setRightRule(rightRule);
        }
    }

    private void buildInitialRules(String input) {
        char[] inputChars = input.toCharArray();

        for (int i = 0; i < inputChars.length - 1; i = i + 1) {
            String pair = String.valueOf(inputChars[i]) + inputChars[i + 1];
            ruleList.add(ruleMapping.get(pair));
        }
    }

    void applyReplacements() {
        Map<Rule, Long> occurrences = new HashMap<>();

        for (Rule rule : rules) {
            occurrences.compute(rule, (ruleKey, count) -> 0L);
        }

        Rule lastInitiallyActiveRule = null;
        for (Rule rule : ruleList) {
            occurrences.compute(rule, (ruleKey, count) -> count + 1);
            lastInitiallyActiveRule = rule;
        }

        for (int i = 1; i < Day14Challenge.STEPS; i++) {
            Map<Rule, Long> tempOccurrences = new HashMap<>();
            occurrences.forEach((rule, count) -> {
                if (count > 0) {
                    tempOccurrences.compute(rule.getLeftRule(), (ruleKey, ruleCount) -> ruleCount == null ? count : ruleCount + count);
                    tempOccurrences.compute(rule.getRightRule(), (ruleKey, ruleCount) -> ruleCount == null ? count : ruleCount + count);
                }
            });
            occurrences = tempOccurrences;
        }

        long[] statistics = new long[26];
        occurrences.forEach((rule, count) -> {
            if (count > 0) {
                System.out.println(rule + " => " + rule.getLeft() + " +" + count + " " + rule.getMiddle() + " +" + count);
                statistics[rule.getLeft() - 'A'] += count;
                statistics[rule.getMiddle() - 'A'] += count;
            }
        });

        statistics[lastInitiallyActiveRule.getRight() - 'A']++;

        for (int i = 0; i < statistics.length; i++) {
            System.out.println((char) ('A' + i) + " : " + statistics[i]);
        }

        long max = statistics[0];
        long min = Long.MAX_VALUE;
        char maxChar = 'A';
        char minChar = 'Z';
        for (int i = 1; i < statistics.length; i++) {
            if (statistics[i] > max) {
                max = statistics[i];
                maxChar = (char) ('A' + i);
            }

            if (statistics[i] < min && statistics[i] > 0) {
                min = statistics[i];
                minChar = (char) ('A' + i);
            }
        }

        System.out.printf("Max char (%c) | (%d) - min char (%c) | (%d) is %d%n", maxChar, max, minChar, min, (max - min));
    }
}
