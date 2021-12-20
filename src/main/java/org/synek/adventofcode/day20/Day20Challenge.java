package org.synek.adventofcode.day20;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import org.synek.adventofcode.util.Util;


// 5525, 5479, 5499, 5573 do not work
public class Day20Challenge {

    private static final String INPUT_RESOURCE = "input";
    private static final int STEPS = 50;

    public static void main(String[] args) {
        Image inputImage = readInputImage(INPUT_RESOURCE);

        Image temp = inputImage;
        for (int i = 0; i < STEPS; i++) {
            temp = temp.remapImage();
        }

        System.out.println("Lit: " + temp.countLit());
    }

    private static Image readInputImage(String resource) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(Util.classpathResourceURI(Day20Challenge.class, resource)));
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }

        StringBuilder enhancementAlgorithmBuilder = new StringBuilder();
        Iterator<String> lineIterator = lines.iterator();
        int algorithmsLineCount = 0;
        while (lineIterator.hasNext()) {
            algorithmsLineCount++;
            String line = lineIterator.next();
            if (line.isBlank()) {
                break;
            }

            enhancementAlgorithmBuilder.append(line);
        }

        boolean [] enhancementAlgorithm = decodeLine(enhancementAlgorithmBuilder.toString());

        boolean [][] inputPixels = new boolean[lines.size() - algorithmsLineCount][];
        int i = 0;
        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            inputPixels[i++] = decodeLine(line);
        }

        return new Image(inputPixels, enhancementAlgorithm);
    }

    private static boolean[] decodeLine(String line) {
        boolean [] values = new boolean[line.length()];

        int i = 0;
        for (char c : line.toCharArray()) {
            values[i++] = c == '#' ? true : false;
        }
        return values;
    }
}
