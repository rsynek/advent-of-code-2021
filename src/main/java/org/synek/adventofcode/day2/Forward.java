package org.synek.adventofcode.day2;

public class Forward extends AbstractMove {

    public Forward(int distance) {
        super(distance);
    }

    @Override
    public Location nextLocation(Location location) {
        int depth = location.depth() + distance * location.aim();
        return new Location(location.position() + distance, depth, location.aim());
    }
}
