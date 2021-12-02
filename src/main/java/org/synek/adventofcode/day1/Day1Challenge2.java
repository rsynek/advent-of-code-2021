package org.synek.adventofcode.day1;

public class Day1Challenge2 extends AbstractCommonDay1Challenge {

    public static void main(String[] args) {
        Day1Challenge2 challenge = new Day1Challenge2();
        int[] surfaceDepths = challenge.readInput();

        int [] slidingWindows = new int[surfaceDepths.length];
        int j = 0;
        for (int i = 2; i < surfaceDepths.length; i = i + 1) {
            slidingWindows[j++] = surfaceDepths[i-2] + surfaceDepths[i-1] + surfaceDepths[i];
        }

        int increased = 0;
        System.out.println(slidingWindows[0]);
        for (int i = 1; i < slidingWindows.length; i++) {
            System.out.print(slidingWindows[i]);
            if (slidingWindows[i] > slidingWindows[i - 1]) {
                increased++;
                System.out.print(" increased");
            }
            System.out.println();
        }

        System.out.println("The surface depth increased " + increased + " times.");
    }
}
