package com.game.tictactoe.service;

import com.game.tictactoe.common.Position;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.GameType;
import com.game.tictactoe.entity.enums.Player;
import com.game.tictactoe.form.AutoMoveForm;
import com.game.tictactoe.form.MoveForm;
import com.game.tictactoe.repository.MoveRepository;
import com.game.tictactoe.service.impl.MoveServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoveServiceTest {

    @Mock
    private MoveRepository moveRepository;

    private MoveService moveService;

    @Before
    public void setUp() {
        moveService = new MoveServiceImpl(moveRepository);
    }

    @Test
    public void createNewGame_SuccessTest() {
        when(moveRepository.save(any(Move.class))).thenReturn(new Move());

        Move newMove = moveService.createNewMove(new Game(), new MoveForm());

        assertNotNull(newMove);
    }

    @Test
    public void autoCreateMove_SuccessTest() {
        Move move = Move.builder()
                .boardRowNumber(1)
                .boardColumnNumber(1)
                .moveNumber(1)
                .game(Game.builder()
                        .type(GameType.ONE_VS_COMPUTER)
                        .status(GameStatus.IN_PROGRESS)
                        .build())
                .player(Player.PLAYER_1)
                .build();
        when(moveRepository.findAllByGame(any(Game.class))).thenReturn(Collections.singletonList(move));
        when(moveRepository.save(any(Move.class))).thenReturn(new Move());

        Move newMove = moveService.autoCreateMove(new Game(), new AutoMoveForm());

        assertNotNull(newMove);
    }

    @Test
    public void checkCurrentGameStatus_SuccessTest() {
        Move move = Move.builder()
                .boardRowNumber(1)
                .boardColumnNumber(1)
                .moveNumber(1)
                .game(Game.builder()
                        .type(GameType.ONE_VS_COMPUTER)
                        .status(GameStatus.IN_PROGRESS)
                        .build())
                .player(Player.PLAYER_1)
                .build();
        when(moveRepository.findAllByGameAndPlayer(any(Game.class), any(Player.class))).thenReturn(Collections.singletonList(move));

        GameStatus gameStatus = moveService.checkCurrentGameStatus(new Game());

        assertNotNull(gameStatus);
        assertEquals(GameStatus.IN_PROGRESS, gameStatus);
    }

    @Test
    public void getMovesInGame_SuccessTest() {
        when(moveRepository.findAllByGame(any(Game.class))).thenReturn(Collections.singletonList(new Move()));

        List<Move> movesInGame = moveService.getMovesInGame(new Game());

        assertNotNull(movesInGame);
        assertFalse(movesInGame.isEmpty());
    }

    @Test
    public void getMovesInGame_NotFoundTest() {
        when(moveRepository.findAllByGame(any(Game.class))).thenReturn(Collections.emptyList());

        List<Move> movesInGame = moveService.getMovesInGame(new Game());

        assertNotNull(movesInGame);
        assertTrue(movesInGame.isEmpty());
    }

    @Test
    public void getTakenMovePositionsInGame_SuccessTest() {
        Move move = Move.builder()
                .boardRowNumber(1)
                .boardColumnNumber(1)
                .moveNumber(1)
                .game(Game.builder()
                        .type(GameType.ONE_VS_COMPUTER)
                        .status(GameStatus.IN_PROGRESS)
                        .build())
                .player(Player.PLAYER_1)
                .build();
        when(moveRepository.findAllByGame(any(Game.class))).thenReturn(Collections.singletonList(move));

        List<Position> positions = moveService.getTakenMovePositionsInGame(new Game());

        assertNotNull(positions);
        assertFalse(positions.isEmpty());
    }

    @Test
    public void getPlayerMovePositionsInGame_SuccessTest() {
        Move move = Move.builder()
                .boardRowNumber(1)
                .boardColumnNumber(1)
                .moveNumber(1)
                .game(Game.builder()
                        .type(GameType.ONE_VS_COMPUTER)
                        .status(GameStatus.IN_PROGRESS)
                        .build())
                .player(Player.PLAYER_1)
                .build();
        when(moveRepository.findAllByGameAndPlayer(any(Game.class), any(Player.class))).thenReturn(Collections.singletonList(move));

        List<Position> positions = moveService.getPlayerMovePositionsInGame(new Game(), Player.PLAYER_1);

        assertNotNull(positions);
        assertFalse(positions.isEmpty());
    }
}
