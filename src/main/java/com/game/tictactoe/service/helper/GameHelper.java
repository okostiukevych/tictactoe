package com.game.tictactoe.service.helper;

import com.game.tictactoe.common.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class GameHelper {

    private static final int BOARDER_SIZE = 9;

    /**
     * Checks if the player wins
     * @param positions Board positions from player moves retrieved from database
     * @return true or false if the player is a winner
     */
    public static boolean isWinner(List<Position> positions) {
        return getWinningPositions().stream()
                .anyMatch(positions::containsAll);
    }

    /**
     * Stores list of winning positions.
     * @return the list of the winning position's list
     */
    private static List<List<Position>> getWinningPositions() {
        List<List<Position>> winingPositions = new ArrayList<>();

        winingPositions.add(asList(new Position(1, 1), new Position(1, 2), new Position(1,3)));
        winingPositions.add(asList(new Position(2, 1), new Position(2, 2), new Position(2,3)));
        winingPositions.add(asList(new Position(3, 1), new Position(3, 2), new Position(3,3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 1), new Position(3,1)));
        winingPositions.add(asList(new Position(1, 2), new Position(2, 2), new Position(3,2)));
        winingPositions.add(asList(new Position(1, 3), new Position(2, 3), new Position(3,3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 2), new Position(3,3)));
        winingPositions.add(asList(new Position(3, 1), new Position(2, 2), new Position(1,3)));

        return winingPositions;
    }

    public static boolean isBoardIsFull(List<Position> takenPositions) {
        return takenPositions.size() == BOARDER_SIZE;
    }

    // GENERATE COMPUTER'S MOVES

    public static Position nextAutoMove(List<Position> takenPositions) {
        List<Position> openPositions = getOpenPositions(takenPositions);
        Random rand = new Random();
        int n = rand.nextInt(openPositions.size() - 1);
        return openPositions.get(n);
    }

    private static List<Position> getOpenPositions(List<Position> takenPositions) {
        return getAllPositions().stream()
                .filter(position -> !takenPositions.contains(position))
                .collect(Collectors.toList());
    }

    /**
     * Stores all pairs of available rows and columns
     * @return list of all board's positions
     */
    private static List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                positions.add(new Position(row, col));
            }
        }
        return positions;
    }
}
