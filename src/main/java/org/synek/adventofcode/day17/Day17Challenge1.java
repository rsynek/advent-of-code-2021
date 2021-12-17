package org.synek.adventofcode.day17;

public class Day17Challenge1 {

    // 5460
    private static final Position TARGET_MIN = new Position(206, -105);
    private static final Position TARGET_MAX = new Position(250, -57);

//    private static final Position TARGET_MIN = new Position(20, -10);
//    private static final Position TARGET_MAX = new Position(30, -5);
    private static final Position START = new Position(0, 0);

    public static void main(String[] args) {

        long value = Math.abs(TARGET_MIN.y()) - 1;

        System.out.println(value * (value + 1) / 2);

        if (true) return;

        int estimateX = 21;
        int estimateY = 104;
        Attempt bestAttempt = null;

        boolean tooHigh = false;
        while (true) {
            Position currentPosition = START;
            Probe probe = new Probe(currentPosition, estimateX, estimateY);
            boolean onTheWay = true;
            int maxY = START.y();
            System.out.println("Attempt vel. X: " + estimateX + ", vel. Y: " + estimateY);
            while (onTheWay) {
                currentPosition = probe.nextStep();
                System.out.println(currentPosition);
                if (currentPosition.y() > maxY) {
                    maxY = currentPosition.y();
                   // System.out.println("maxY: " + maxY);
                }
                onTheWay = !missedTargetArea(currentPosition) && !hitTargetArea(currentPosition);
            }

            if (hitTargetArea(currentPosition)) {
                System.out.println("Hit! maxY: " + maxY);
                System.out.println(currentPosition);
                if (bestAttempt == null || maxY > bestAttempt.maxHeight()) {
                    bestAttempt = new Attempt(estimateX, estimateY, maxY);
                }

                estimateY = estimateY + 1;
            }

            if (missedTargetArea(currentPosition)) {
                System.out.println("Missed: " + currentPosition);
                if (currentPosition.x() > TARGET_MAX.x()) { // too far
                    break;
                }

                if (currentPosition.x() > TARGET_MIN.x() && currentPosition.y() < TARGET_MIN.y()) { // too high
                    estimateY = estimateY - 1;
                    tooHigh = true;
                }

                if (currentPosition.x() < TARGET_MIN.x()) {
                    estimateX = estimateX + 1;
                    estimateY = estimateY + 1;
                }
            }

            if (tooHigh && bestAttempt != null) {
                break;
            }
        }

        System.out.println("Best Attempt: " + bestAttempt);
    }

    static boolean hitTargetArea(Position position) {
        return (position.x() >= TARGET_MIN.x() && position.x() <= TARGET_MAX.x()
                && position.y() >= TARGET_MIN.y() && position.y() <= TARGET_MAX.y());
    }

    static boolean missedTargetArea(Position position) {
        return (position.y() < TARGET_MIN.y()) || position.x() > TARGET_MAX.x();
    }

    private record Position(int x, int y) {
    }

    private record Attempt(int velocityX, int velocityY, int maxHeight) {
    }

    private static class Probe {
        private Position position;
        private int velocityX;
        private int velocityY;

        public Probe(Position position, int velocityX, int velocityY) {
            this.position = position;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
        }

        Position nextStep() {
            if (velocityY > 5000) {
                System.out.println("Warning!" + this.position + " vel: " + velocityY);
            }
            position = new Position(position.x() + velocityX, position.y() + velocityY);
            velocityX = velocityX > 0 ? velocityX - 1 : velocityX < 0 ? velocityX + 1 : 0;
//            if (velocityX == 0 && velocityY > 0) {
//                velocityY = 0;
//            } else {
//                velocityY--;
//            }
            velocityY--;

            return position;
        }
    }
}
