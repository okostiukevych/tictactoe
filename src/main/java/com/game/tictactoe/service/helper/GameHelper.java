package com.game.tictactoe.service.helper;

import com.game.tictactoe.common.Position;
import com.game.tictactoe.entity.enums.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class GameHelper {

    private static final int BOARDER_SIZE = 9;

    private static List<Position> intermediatePositions = new ArrayList<>();

    /**
     * Checks if the player wins
     *
     * @param positions Board positions from player moves retrieved from database
     * @return true or false if the player is a winner
     */
    public static boolean isWinner(List<Position> positions) {
        return getWinningPositions().stream()
                .anyMatch(positions::containsAll);
    }

    public static boolean boardIsFullFilled(List<Position> takenPositions) {
        return takenPositions.size() == BOARDER_SIZE;
    }

    // GENERATE COMPUTER'S MOVES

    public static Position nextAutoMove(List<Position> takenPositions) {
        List<Position> openPositions = getOpenPositions(takenPositions);
        return getMostValuablePosition(takenPositions, openPositions);
    }

    private static List<Position> getOpenPositions(List<Position> takenPositions) {
        return getAllPositions().stream()
                .filter(position -> !takenPositions.contains(position))
                .collect(toList());
    }

    private static Position getMostValuablePosition(List<Position> takenPositions,
                                                    List<Position> openPositions) {
        Position positionToWinGame = getPositionToWinOrSaveGame(takenPositions, positions -> sameComputerPositions());
        if (nonNull(positionToWinGame)) {
            return positionToWinGame;
        }
        Position positionToSaveGame = getPositionToWinOrSaveGame(takenPositions, positions -> samePlayerPositions());
        return nonNull(positionToSaveGame)
                ? positionToSaveGame
                : openPositions.get(getRandomFreePosition(openPositions.size()));
    }

    private static boolean samePlayerPositions() {
        return intermediatePositions.stream()
                .allMatch(position -> Player.PLAYER_1.equals(position.getPlayer()));
    }

    private static boolean sameComputerPositions() {
        return intermediatePositions.stream()
                .allMatch(position -> Player.COMPUTER.equals(position.getPlayer()));
    }

    private static Position getPositionToWinOrSaveGame(List<Position> takenPositions, Predicate<Position> customPredicate) {
        List<List<Position>> winningPositions = getWinningPositions();
        int counter = 0;
        for (List<Position> winPosition : winningPositions) {
            for (Position position : winPosition) {
                if (takenPositions.contains(position)) {
                    counter++;
                    intermediatePositions.add(getPositionWithPlayer(position, takenPositions));
                }
            }
            boolean positionsByOnePlayer = intermediatePositions.stream()
                    .allMatch(customPredicate);
            if (counter > 1 && positionsByOnePlayer) {
                return winPosition.stream()
                        .filter(position -> !intermediatePositions.contains(position))
                        .findFirst()
                        .orElse(null);
            } else {
                counter = 0;
                intermediatePositions.clear();
            }
        }
        return null;
    }

    private static Position getPositionWithPlayer(Position position, List<Position> takenPositions) {
        return takenPositions.stream()
                .filter(p -> p.equals(position)).findFirst()
                .get();
    }

    private static int getRandomFreePosition(int size) {
        Random rand = new Random();
        return rand.nextInt(size - 1);
    }

    /**
     * Stores all pairs of available rows and columns
     *
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

    /**
     * Stores list of winning positions.
     *
     * @return the list of the winning position's list
     */
    private static List<List<Position>> getWinningPositions() {
        List<List<Position>> winingPositions = new ArrayList<>();

        winingPositions.add(asList(new Position(1, 1), new Position(1, 2), new Position(1, 3)));
        winingPositions.add(asList(new Position(2, 1), new Position(2, 2), new Position(2, 3)));
        winingPositions.add(asList(new Position(3, 1), new Position(3, 2), new Position(3, 3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 1), new Position(3, 1)));
        winingPositions.add(asList(new Position(1, 2), new Position(2, 2), new Position(3, 2)));
        winingPositions.add(asList(new Position(1, 3), new Position(2, 3), new Position(3, 3)));

        winingPositions.add(asList(new Position(1, 1), new Position(2, 2), new Position(3, 3)));
        winingPositions.add(asList(new Position(3, 1), new Position(2, 2), new Position(1, 3)));

        return winingPositions;
    }
}
