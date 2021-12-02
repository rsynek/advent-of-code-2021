package org.synek.adventofcode.day2;

public final class Up extends AbstractMove {

    public Up(int distance) {
        super(distance);
    }

    @Override
    public Location nextLocation(Location location) {
        return new Location(location.position(), location.depth(), location.aim() - distance);
    }
}
