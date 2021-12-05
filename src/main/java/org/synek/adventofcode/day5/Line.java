package org.synek.adventofcode.day5;

public class Line {

    public final int startX;
    public final int startY;
    public final int endX;
    public final int endY;

    public Line(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public boolean isHorizontal() {
        return startY == endY;
    }

    public boolean isVertical() {
        return startX == endX;
    }

    public boolean isDiagonal() {
        return Math.abs(startX - endX) == Math.abs(startY - endY);
    }

    public boolean isRaisingDiagonal() {
        return isDiagonal() && (startY > endY && startX > endX) || (startX < endX && startY < endY);
    }

    @Override
    public String toString() {
        return String.format("%d,%d -> %d,%d", startX, startY, endX, endY);
    }
}
