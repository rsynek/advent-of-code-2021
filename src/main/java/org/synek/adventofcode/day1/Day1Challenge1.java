package org.synek.adventofcode.day1;

public class Day1Challenge1 extends AbstractCommonDay1Challenge {

    public static void main(String[] args) {
        Day1Challenge1 challenge = new Day1Challenge1();
        int[] surfaceDepths = challenge.readInput();

        int increased = 0;
        System.out.println(surfaceDepths[0]);
        for (int i = 1; i < surfaceDepths.length; i++) {
            System.out.print(surfaceDepths[i]);
            if (surfaceDepths[i] > surfaceDepths[i - 1]) {
                increased++;
                System.out.print(" increased");
            }
            System.out.println();
        }

        System.out.println("The surface depth increased " + increased + " times.");
    }

}
