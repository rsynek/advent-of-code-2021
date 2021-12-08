package org.synek.adventofcode.day8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.synek.adventofcode.util.Util;

abstract class CommonDay1Challenge {

    protected static List<CodePair> readCodePairs(String resource) {
        String content;
        try {
            content = Files.readString(Path.of(Util.classpathResourceURI(Day8Challenge1.class, resource)));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        String[] lines = content.split("[|\n]");
        List<CodePair> codePairs = new ArrayList<>();
        boolean odd = true;
        String[] signals = null;
        String[] digits = null;
        for (String line : lines) {
            String sanitizedLine = line.trim();
            if (sanitizedLine.isEmpty()) {
                continue;
            }
            String[] segments = sanitizedLine.split(" ");
            if (odd) {
                signals = segments;
            } else {
                digits = segments;
            }
            if (!odd) {
                codePairs.add(new CodePair(signals, digits));
            }
            odd = !odd;
        }
        return codePairs;
    }
}
