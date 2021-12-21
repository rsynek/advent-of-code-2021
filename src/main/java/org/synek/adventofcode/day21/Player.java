package org.synek.adventofcode.day21;

import static org.synek.adventofcode.day21.Game.BOARD;

public class Player {

    private int id;
    private int position;
    private int score = 0;

    public Player(int id, int position) {
        this.id = id;
        this.position = position;
    }

    public int move(int steps) {
        position = position + steps;
        if (position > 10) {
            if (position % BOARD == 0) {
                position = BOARD;
            } else {
                position = position % BOARD;
            }
        }
        score += position;
        return score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Player" + id + " {" +
                "position=" + position +
                ", score=" + score +
                '}';
    }
}
