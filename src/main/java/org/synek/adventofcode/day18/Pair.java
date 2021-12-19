package org.synek.adventofcode.day18;

import java.util.Optional;

public class Pair extends PairOrValue {

    private PairOrValue left;
    private PairOrValue right;

    public Pair() {
    }

    public Pair(PairOrValue left, PairOrValue right) {
        this.left = left;
        this.right = right;
    }

    public Optional<Pair> findPairToExplode(int depth) {
        if (depth == 4) {
            return Optional.of(this);
        }

        Optional<Pair> explodablePair = left.findPairToExplode(depth + 1);
        if (explodablePair.isPresent()) {
            return explodablePair;
        } else {
            return right.findPairToExplode(depth + 1);
        }
    }

    public Value explode() {
        if (!(left instanceof Value leftValue) || !(right instanceof Value rightValue)) {
            throw new IllegalStateException("Non-terminal pair (" + this + ") cannot be exploded.");
        }
        Value newValue = new Value(0);
        Pair parent = getParent();
        if (parent.getLeft() == this) {
            parent.setLeft(newValue);
        } else {
            parent.setRight(newValue);
        }

        if (leftValue.getLeft() != null) {
            leftValue.getLeft().add(leftValue.getVal());
            leftValue.getLeft().setRight(newValue); // reconnect the list of values
            newValue.setLeft(leftValue.getLeft());
        }

        if (rightValue.getRight() != null) {
            rightValue.getRight().add(rightValue.getVal());
            rightValue.getRight().setLeft(newValue); // reconnect the list of values
            newValue.setRight(rightValue.getRight());
        }

        newValue.setParent(parent);

        return newValue;
    }

    @Override
    public long magnitude() {
        return 3 * left.magnitude() + 2 * right.magnitude();
    }

    public PairOrValue getLeft() {
        return left;
    }

    public void setLeft(PairOrValue left) {
        this.left = left;
    }

    public PairOrValue getRight() {
        return right;
    }

    public void setRight(PairOrValue right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + "," + right.toString() + "]";
    }
}
