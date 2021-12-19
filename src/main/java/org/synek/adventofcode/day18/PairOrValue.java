package org.synek.adventofcode.day18;

import java.util.Optional;

abstract class PairOrValue {

    private Pair parent;

    public Pair getParent() {
        return parent;
    }

    public void setParent(Pair parent) {
        this.parent = parent;
    }

    public abstract Optional<Pair> findPairToExplode(int depth);

    public abstract long magnitude();

}
