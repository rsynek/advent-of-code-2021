package org.synek.adventofcode.day14;

public class Rule {

    private final char left;
    private final char right;
    private final char middle;

    private Rule leftRule;
    private Rule rightRule;

    public Rule(char left, char right, char middle) {
        this.left = left;
        this.right = right;
        this.middle = middle;
    }

    public char getLeft() {
        return left;
    }

    public char getRight() {
        return right;
    }

    public char getMiddle() {
        return middle;
    }

    public Rule getLeftRule() {
        return leftRule;
    }

    public void setLeftRule(Rule leftRule) {
        this.leftRule = leftRule;
    }

    public Rule getRightRule() {
        return rightRule;
    }

    public void setRightRule(Rule rightRule) {
        this.rightRule = rightRule;
    }

    @Override
    public String toString() {
        return left + "" + right + "->" + middle;
    }
}
