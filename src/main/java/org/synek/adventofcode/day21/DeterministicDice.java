package org.synek.adventofcode.day21;

public class DeterministicDice {

    private int lastNumber = 0;
    private int rolled = 0;

    public int roll() {
        rolled++;
        lastNumber++;
        if (lastNumber == 101) {
            lastNumber = 1;
        }

        return lastNumber;
    }

    public int getRolled() {
        return rolled;
    }
}
