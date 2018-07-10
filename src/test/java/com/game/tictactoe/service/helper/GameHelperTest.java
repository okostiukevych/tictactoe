package com.game.tictactoe.service.helper;

import com.game.tictactoe.common.Position;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class GameHelperTest {

    @Test
    public void isWinner_WinnerTest() {
        List<Position> positions = asList(
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 3));
        boolean isWinner = GameHelper.isWinner(positions);

        assertTrue(isWinner);
    }

    @Test
    public void isWinner_NotWinnerTest() {
        List<Position> positions = asList(
                new Position(1, 1),
                new Position(1, 2));
        boolean isWinner = GameHelper.isWinner(positions);

        assertFalse(isWinner);
    }

    @Test
    public void boardIsFullFilled_FullFilledTest() {
        List<Position> positions = asList(
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 3),
                new Position(2, 1),
                new Position(2, 2),
                new Position(2, 3),
                new Position(3, 1),
                new Position(3, 2),
                new Position(3, 3));
        boolean isFullFilled = GameHelper.boardIsFullFilled(positions);

        assertTrue(isFullFilled);
    }

    @Test
    public void boardIsFullFilled_NotFullFilledTest() {
        List<Position> positions = asList(
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 3),
                new Position(2, 1),
                new Position(2, 2),
                new Position(2, 3));
        boolean isFullFilled = GameHelper.boardIsFullFilled(positions);

        assertFalse(isFullFilled);
    }

    @Test
    public void nextAutoMove_SuccessTest() {
        List<Position> positions = asList(
                new Position(1, 1),
                new Position(1, 2),
                new Position(1, 3),
                new Position(2, 1));
        Position position = GameHelper.nextAutoMove(positions);

        assertNotNull(position);
    }
}
