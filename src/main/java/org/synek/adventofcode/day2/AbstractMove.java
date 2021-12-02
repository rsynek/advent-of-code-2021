package org.synek.adventofcode.day2;

import org.synek.adventofcode.util.Util;

abstract class AbstractMove {
    protected final int distance;

    protected AbstractMove(int distance) {
        this.distance = Util.requireNotNegative(distance);
    }

    public abstract Location nextLocation(Location location);
}
