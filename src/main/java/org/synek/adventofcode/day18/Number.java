package org.synek.adventofcode.day18;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class Number {

    public static Number parse(String line) {
        Deque<Pair> pairDeque = new ArrayDeque<>();
        LinkedList<Value> valueList = new LinkedList<>();
        Pair topLevelPair = null;
        char[] chars = line.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            boolean commaBefore = (i > 0 && chars[i - 1] == ',');
            char c = chars[i];
            if (c == '[') { // pair started
                Pair lastPair = pairDeque.peek();
                Pair newPair = new Pair();
                if (lastPair != null) {
                    if (commaBefore) {
                        lastPair.setRight(newPair);
                    } else {
                        lastPair.setLeft(newPair);
                    }
                    newPair.setParent(lastPair);
                }
                pairDeque.push(newPair);
            } else if (c == ']') { // pair closed
                Pair closingPair = pairDeque.pop();
                Pair lastPair = pairDeque.peek();
                if (lastPair != null) {
                    closingPair.setParent(lastPair);
                }
                topLevelPair = closingPair;
            } else if (c >= '0' && c <= '9') { // value
                Value value = new Value(c - '0');

                Pair lastPair = pairDeque.peek();
                value.setParent(lastPair);
                if (commaBefore) {
                    lastPair.setRight(value);
                } else {
                    lastPair.setLeft(value);
                }

                if (!valueList.isEmpty()) { // connect the list of values
                    Value lastValue = valueList.getLast();
                    lastValue.setRight(value);
                    value.setLeft(lastValue);
                }
                valueList.add(value);
            } else if (c == ',') { // comma we skip as we do look behind in other branches
                continue;
            } else {
                throw new IllegalArgumentException(String.format("Unrecognizable character (%c) in line (%s).", c, line));
            }
        }

        return new Number(topLevelPair, valueList.getFirst(), valueList.getLast());
    }

    private final Pair topLevelPair;
    private Value leftMostValue;
    private Value rightMostValue;

    private Number(Pair topLevelPair, Value leftMostValue, Value rightMostValue) {
        this.topLevelPair = topLevelPair;
        this.leftMostValue = leftMostValue;
        this.rightMostValue = rightMostValue;
    }

    public Number add(Number other) {
        Pair newTopLevelPair = new Pair(topLevelPair, other.topLevelPair);
        topLevelPair.setParent(newTopLevelPair);
        other.topLevelPair.setParent(newTopLevelPair);
        Number newNumber = new Number(newTopLevelPair, this.leftMostValue, other.rightMostValue);
        this.rightMostValue.setRight(other.leftMostValue);
        other.leftMostValue.setLeft(this.rightMostValue);

        return newNumber.reduce();
    }

    public Number reduce() {
        System.out.println(this);
        while (explode() || splitLeftMost()) {
       //     System.out.println(this);
        }
        System.out.println("Reduced:");
        System.out.println(this);
        System.out.println("---------------------------------------");
        return this;
    }

    public long magnitude() {
        return topLevelPair.magnitude();
    }

    private boolean explode() {
        Optional<Pair> pairToExplode = findPairToExplode();
        if (pairToExplode.isPresent()) {
            Value newValue = pairToExplode.get().explode();
            if (newValue.getLeft() == null) {
                leftMostValue = newValue;
            }
            if (newValue.getRight() == null) {
                rightMostValue = newValue;
            }
        }

        return pairToExplode.isPresent();
    }

    private Optional<Pair> findPairToExplode() {
        return topLevelPair.findPairToExplode(0);
    }

    private boolean splitLeftMost() {
        Value value = leftMostValue;
        while (value != null) {
            if (value.split(this)) {
                return true;
            }
            value = value.getRight();
        }
        return false;
    }

    void setLeftMostValue(Value leftMostValue) {
        this.leftMostValue = leftMostValue;
        leftMostValue.setLeft(null);
    }

    void setRightMostValue(Value rightMostValue) {
        this.rightMostValue = rightMostValue;
        rightMostValue.setRight(null);
    }

    @Override
    public String toString() {
        return topLevelPair.toString();
    }
}
