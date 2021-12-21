package org.synek.adventofcode.day21;

public class Game {

    static final int BOARD = 10;
    private final static int WINNING_SCORE = 1000;

    private final Player firstPlayer;
    private final Player secondPlayer;
    private final DeterministicDice dice = new DeterministicDice();

    public Game(int startingPositionPlayerA, int startingPositionPlayerB) {
        this.firstPlayer = new Player(1, startingPositionPlayerA);
        this.secondPlayer = new Player(2, startingPositionPlayerB);
    }

    private boolean nextStep() {
        return nextStep(firstPlayer) && nextStep(secondPlayer);
    }

    private boolean nextStep(Player player) {
        int score = player.move(rollDiceThreeTimes());
        System.out.println(player);
        return (score < WINNING_SCORE);
    }

    public int play() {
        while (nextStep());

        Player looser = getLooser();
        return looser.getScore() * dice.getRolled();
    }

    private Player getWinner() {
        if (firstPlayer.getScore() >= WINNING_SCORE) {
            return firstPlayer;
        } else if (secondPlayer.getScore() >= WINNING_SCORE) {
            return secondPlayer;
        } else {
            throw new IllegalStateException("No one has won yet.");
        }
    }

    private Player getLooser() {
        if (firstPlayer.getScore() >= WINNING_SCORE) {
            return secondPlayer;
        } else if (secondPlayer.getScore() >= WINNING_SCORE) {
            return firstPlayer;
        } else {
            throw new IllegalStateException("No one has won yet.");
        }
    }

    private int rollDiceThreeTimes() {
        int firstRoll = dice.roll();
        int secondRoll = dice.roll();
        int thirdRoll = dice.roll();

        System.out.println(String.format("Rolls (%d) %d + %d + %d", dice.getRolled(), firstRoll, secondRoll, thirdRoll));
        return firstRoll + secondRoll + thirdRoll;
    }
}
