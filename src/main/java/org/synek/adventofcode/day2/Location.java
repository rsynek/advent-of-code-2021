package org.synek.adventofcode.day2;

import org.synek.adventofcode.util.Util;

public record Location(int position, int depth, int aim) {

    public Location(int position, int depth, int aim) {
        this.position = Util.requireNotNegative(position);
        this.depth = Util.requireNotNegative(depth);
        this.aim = Util.requireNotNegative(aim);
    }

    public int calculateLocation() {
        return position * depth;
    }

    @Override
    public String toString() {
        return "Location{" +
                "position=" + position +
                ", depth=" + depth +
                ", result=" + calculateLocation() +
                '}';
    }
}
