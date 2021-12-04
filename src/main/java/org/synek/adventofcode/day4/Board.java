package org.synek.adventofcode.day4;

public class Board {
    public static final int BOARD_SIZE = 5;
    private static final int MARKED = -1;

    private final int[][] numbers;
    private int completeRowIndex = -1;
    private int completeColumnIndex = -1;

    public Board(int[][] numbers) {
        this.numbers = numbers;
    }

    public boolean isCompleted() {
        return completeColumnIndex >= 0 || completeRowIndex >= 0;
    }

    public void mark(int number) {
        int markedInRow;
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            markedInRow = 0;
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (numbers[rowIndex][j] == number) {
                    numbers[rowIndex][j] = MARKED;
                }
                if (numbers[rowIndex][j] == MARKED) {
                    markedInRow++;
                }
            }
            if (markedInRow == BOARD_SIZE) {
                completeRowIndex = rowIndex;
            }
        }

        int markedInColumn;
        for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
            markedInColumn = 0;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (numbers[i][columnIndex] == MARKED) {
                    markedInColumn++;
                }
            }
            if (markedInColumn == BOARD_SIZE) {
                completeColumnIndex = columnIndex;
            }
        }
    }

    public int score() {
        if (!isCompleted()) {
            throw new IllegalStateException("This board has not been completed yet.");
        }
        int score = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (numbers[i][j] != MARKED) {
                    score += numbers[i][j];
                }
            }
        }
        return score;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(numbers[i][j]).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
