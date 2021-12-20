package org.synek.adventofcode.day20;

public class Image {

    private static final int NEIGHBOURHOOD = 9;

    private final boolean[][] pixels;
    private final boolean[] enhancementAlgorithm;
    private boolean defaultValue;

    public Image(boolean[][] pixels, boolean[] enhancementAlgorithm, boolean defaultValue) {
        this.defaultValue = defaultValue;
        this.pixels = pixels;
        this.enhancementAlgorithm = enhancementAlgorithm;
    }

    public Image(boolean[][] pixels, boolean[] enhancementAlgorithm) {
        this(pixels, enhancementAlgorithm, false);
    }

    public Image remapImage() {
        // enlarge
        boolean[][] outputPixels = new boolean[pixels.length + 2][];
        for (int i = 0; i < outputPixels.length; i++) {
            outputPixels[i] = new boolean[pixels[0].length + 2];
        }

        // copy
        for (int i = 0; i < outputPixels[0].length; i++) {
            outputPixels[0][i] = defaultValue;
            outputPixels[outputPixels.length - 1][i] = defaultValue;
        }

        for (int i = 0; i < outputPixels.length; i++) {
            outputPixels[i][0] = defaultValue;
            outputPixels[i][outputPixels[0].length - 1] = defaultValue;
        }

        for (int i = 1; i < outputPixels.length - 1; i++) {
            for (int j = 1; j < outputPixels[0].length - 1; j++) {
                outputPixels[i][j] = pixels[i - 1][j - 1];
            }
        }

        boolean [][] copy = new boolean[outputPixels.length][];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = new boolean[outputPixels[0].length];
            for (int j = 0; j < copy[0].length; j++) {
                copy[i][j] = outputPixels[i][j];
            }
        }

        // remap
        for (int i = 0; i < outputPixels.length; i++) {
            for (int j = 0; j < outputPixels[0].length; j++) {
                outputPixels[i][j] = remapPixel(copy, i, j);
            }
        }

        return new Image(outputPixels, enhancementAlgorithm, remapPixel(outputPixels,-100, -100));
    }

    public void print() {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                System.out.print(pixels[i][j] ? '#' : '.');
            }
            System.out.println();
        }
    }

    public int countLit() {
        int count = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                if (pixels[i][j]) count++;
            }
        }

        return count;
    }

    private boolean remapPixel(boolean[][] outputPixels, int row, int column) {
        boolean[] binaryCode = new boolean[NEIGHBOURHOOD];
        int index = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                binaryCode[index++] = valueOf(outputPixels, i, j);
            }
        }

        int binaryCodeValue = 0;
        int step = 0;
        for (int i = binaryCode.length - 1; i >= 0; i--) {
            binaryCodeValue += binaryCode[i] ? Math.pow(2, step) : 0;
            step++;
        }
        boolean newValue = enhancementAlgorithm[binaryCodeValue];
        return newValue;
    }

    private boolean valueOf(boolean[][] outputPixels, int row, int column) {
        if (row < 0 || row >= outputPixels.length || column < 0 || column >= outputPixels[0].length) {
            return defaultValue;
        } else {
            return outputPixels[row][column];
        }
    }
}
