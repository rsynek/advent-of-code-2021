package org.synek.adventofcode.day2;

public class Down extends AbstractMove {

    public Down(int distance) {
        super(distance);
    }

    @Override
    public Location nextLocation(Location location) {
        return new Location(location.position(), location.depth(), location.aim() + distance);
    }
}
