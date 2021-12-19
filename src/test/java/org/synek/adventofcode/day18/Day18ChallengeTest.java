package org.synek.adventofcode.day18;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Day18ChallengeTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
            "[[[5,[2,8]],4],[5,[[9,9],0]]]",
            "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
            "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
            "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
            "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
            "[[[[5,4],[7,7]],8],[[8,3],8]]",
            "[[9,3],[[9,9],[6,[4,9]]]]",
            "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
            "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
    })
    void parseNumber(String inputNumber) {
        Number number = Number.parse(inputNumber);
        Assertions.assertThat(number.toString()).isEqualTo(inputNumber);
    }

    @Test
    void addWithoutReduction() {
        Number left = Number.parse("[1,2]");
        Number right = Number.parse("[[3,4],5]");
        Number sum = left.add(right);
        Assertions.assertThat(sum.toString()).isEqualTo("[[1,2],[[3,4],5]]");
    }

    @Test
    void reduce() {
        Number number = Number.parse("[[[[[9,8],1],2],3],4]");
        Assertions.assertThat(number.reduce().toString()).isEqualTo("[[[[0,9],2],3],4]");
    }

    @Test
    void addWithReduction() {
        Number left = Number.parse("[[[[4,3],4],4],[7,[[8,4],9]]]");
        Number right = Number.parse("[1,1]");
        Assertions.assertThat(left.add(right).toString()).isEqualTo("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
    }

    @Test
    void magnitude() {
        Number number = Number.parse("[[1,2],[[3,4],5]]");
        Assertions.assertThat(number.magnitude()).isEqualTo(143);
    }
}
