package org.synek.adventofcode.day13;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class Day13ChallengeTest {

    @Test
    void foldUpBeforeHalf() {
        boolean[][] input = {
                {false, true, false},
                {false, false, false},
                {false, false, false},
                {true, false, true}
        };

        final boolean[][] expectedOutput = {
                {true, false, true},
                {false, true, false}
        };

        Day13Challenge day13Challenge = new Day13Challenge();
        final boolean[][] output = day13Challenge.foldUp(input, 1);
        Assertions.assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void foldUpAfterHalf() {
        boolean[][] input = {
                {false, true, false},
                {false, false, false},
                {false, false, false},
                {true, false, true}
        };

        final boolean[][] expectedOutput = {
                {false, true, false},
                {true, false, true}
        };

        Day13Challenge day13Challenge = new Day13Challenge();
        final boolean[][] output = day13Challenge.foldUp(input, 2);
        Assertions.assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void foldLeftBeforeHalf() {
        boolean[][] input = {
                {false, true, false, true}
        };
        final boolean[][] expectedOutput = {
                {true, false}
        };
        Day13Challenge day13Challenge = new Day13Challenge();
        final boolean[][] output = day13Challenge.foldLeft(input, 1);
        Assertions.assertThat(output).isEqualTo(expectedOutput);
    }

    @Test
    void foldLeftAfterHalf() {
        boolean[][] input = {
                {false, true, false, true}
        };
        final boolean[][] expectedOutput = {
                {false, true}
        };
        Day13Challenge day13Challenge = new Day13Challenge();
        final boolean[][] output = day13Challenge.foldLeft(input, 2);
        Assertions.assertThat(output).isEqualTo(expectedOutput);
    }
}
