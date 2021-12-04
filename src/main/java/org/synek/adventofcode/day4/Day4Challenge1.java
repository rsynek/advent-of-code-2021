package org.synek.adventofcode.day4;

import static org.synek.adventofcode.day4.Board.BOARD_SIZE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day4Challenge1 {

    private static final String INPUT_RESOURCE = "input";

    private final int[] numbers;
    private final List<Board> boardList = new ArrayList<>();

    public Day4Challenge1(String resource) {
        Scanner scanner = new Scanner(Objects.requireNonNull(getClass().getResourceAsStream(resource)));

        this.numbers = readNumbers(scanner.nextLine(), ",");
        int[][] matrix = new int[BOARD_SIZE][BOARD_SIZE];
        int boardRowIndex = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                matrix[boardRowIndex++] = readBoardRow(line);
            }
            if (boardRowIndex == BOARD_SIZE) {
                boardList.add(new Board(matrix));
                matrix = new int[BOARD_SIZE][BOARD_SIZE];
                boardRowIndex = 0;
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        int score = new Day4Challenge1(INPUT_RESOURCE).playGame();
        System.out.println("Score: " + score);
    }


    public int playGame() {
        for (int number : numbers) { // main loop of the game
            System.out.println("[Playing number " + number + "]");
            for (Board board : boardList) {
                System.out.println(board);
                System.out.println("---------------------------------------------------------");
                board.mark(number);
                if (board.isCompleted()) {
                    return board.score() * number;
                }
            }
        }
        throw new IllegalStateException("No board has won.");
    }

    private int[] readNumbers(String line, String delimiter) {
        String[] tokens = line.trim().split(delimiter);
        return Arrays.stream(tokens).mapToInt(Integer::parseInt).toArray();
    }

    private int[] readBoardRow(String line) {
        return readNumbers(line, "\\s+");
    }
}
