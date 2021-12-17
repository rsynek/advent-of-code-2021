package org.synek.adventofcode.day17;

import java.util.HashSet;
import java.util.Set;

public class Day17Challenge2 {

    private static final Pair TARGET_MIN = new Pair(206, -105);
    private static final Pair TARGET_MAX = new Pair(250, -57);

//    private static final Pair TARGET_MIN = new Pair(20, -10);
//    private static final Pair TARGET_MAX = new Pair(30, -5);

    public static void main(String[] args) {
        final int minVelocityX = minimalX();
        final int maxVelocityY = Math.abs(TARGET_MIN.y()) - 1;
        final int minVelocityY = TARGET_MIN.y();
        final int maxVelocityX = TARGET_MAX.x();
        System.out.println(minVelocityX);

        Set<Pair> velocities = new HashSet<>();
        for (int x = minVelocityX; x <= maxVelocityX; x++) {
            for (int y = minVelocityY; y <= maxVelocityY; y++) {
                if (hitsTargetArea(x, y)) {
                    velocities.add(new Pair(x, y));
                }
            }
        }

        System.out.println(velocities.size());
        System.out.println("------------------");
        velocities.forEach(System.out::println);
    }

    private static boolean hitsTargetArea(int x, int y) {
        int velocityX = x;
        int velocityY = y;
        int positionX = 0;
        int positionY = 0;
        do {
            positionX += velocityX;
            velocityX = velocityX == 0 ? 0 : velocityX - 1;

            positionY += velocityY;
            velocityY--;
        } while (positionX < TARGET_MIN.x() || positionY > TARGET_MAX.y());

        return isInsideTargetArea(positionX, positionY);
    }

    private static int minimalX() {
        int minVelocity = 0;
        int positionX = 0;
        while (positionX < TARGET_MIN.x() && positionX < TARGET_MAX.x()) {
            minVelocity++;
            //      System.out.println("Initial velocity: " + minVelocity);
            positionX = 0;
            for (int velocity = minVelocity; velocity > 0; velocity--) {
                positionX = positionX + velocity;
                //     System.out.println("Position: " + positionX);
            }
        }
        return minVelocity;
    }

    static boolean isInsideTargetArea(int x, int y) {
        return (x >= TARGET_MIN.x() && x <= TARGET_MAX.x()
                && y >= TARGET_MIN.y() && y <= TARGET_MAX.y());
    }

    private record Pair(int x, int y) {

        @Override
        public String toString() {
            return "(" +
                    "x=" + x +
                    ", y=" + y +
                    ')';
        }
    }

}
