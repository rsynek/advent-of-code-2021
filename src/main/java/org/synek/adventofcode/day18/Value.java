package org.synek.adventofcode.day18;

import java.util.Optional;

public class Value extends PairOrValue {
    private int val;

    private Value left;
    private Value right;

    public Value(int val) {
        this.val = val;
    }

    public void add(int amount) {
        this.val += amount;
    }

    public boolean split(Number number) {
        if (val >= 10) {
            Value newPairLeftValue = new Value(val / 2);
            Value newPairRightValue = new Value((int) Math.ceil((double)val / 2));
            Pair newPair = new Pair(newPairLeftValue, newPairRightValue);
            Pair parent = getParent();
            if (parent.getLeft() == this) {
                parent.setLeft(newPair);
            } else if (parent.getRight() == this) {
                parent.setRight(newPair);
            }
            newPair.setParent(parent);

            if (left == null) { // we need to update the leftmost value
                number.setLeftMostValue(newPairLeftValue);
            } else {
                left.setRight(newPairLeftValue);
            }
            if (right == null) { // we need to update the rightmost value
                number.setRightMostValue(newPairRightValue);
            } else {
                right.setLeft(newPairRightValue);
            }

            newPairLeftValue.setParent(newPair);
            newPairLeftValue.setLeft(left);
            newPairLeftValue.setRight(newPairRightValue);

            newPairRightValue.setParent(newPair);
            newPairRightValue.setLeft(newPairLeftValue);
            newPairRightValue.setRight(right);

            return true;
        }
        return false;
    }

    @Override
    public Optional<Pair> findPairToExplode(int depth) {
        return Optional.empty();
    }

    @Override
    public long magnitude() {
        return val;
    }

    public void setLeft(Value left) {
        this.left = left;
    }

    public void setRight(Value right) {
        this.right = right;
    }

    public int getVal() {
        return val;
    }

    public Value getLeft() {
        return left;
    }

    public Value getRight() {
        return right;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }
}
