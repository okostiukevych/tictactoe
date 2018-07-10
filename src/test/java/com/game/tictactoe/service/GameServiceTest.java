package com.game.tictactoe.service;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.GameType;
import com.game.tictactoe.form.GameForm;
import com.game.tictactoe.repository.GameRepository;
import com.game.tictactoe.service.impl.GameServiceImpl;
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
public class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    private GameService gameService;

    @Before
    public void setUp() {
        gameService = new GameServiceImpl(gameRepository);
    }

    @Test
    public void getById_SuccessTest() {
        when(gameRepository.findOne(1)).thenReturn(new Game());

        Game game = gameService.getById(1);

        assertNotNull(game);
    }

    @Test
    public void getById_NotFoundTest() {
        when(gameRepository.findOne(1)).thenReturn(null);

        Game game = gameService.getById(1);

        assertNull(game);
    }

    @Test
    public void getByName_SuccessTest() {
        when(gameRepository.findByName("first")).thenReturn(new Game());

        Game game = gameService.getByName("first");

        assertNotNull(game);
    }

    @Test
    public void getByName_NotFoundTest() {
        when(gameRepository.findByName("first")).thenReturn(null);

        Game game = gameService.getByName("first");

        assertNull(game);
    }

    @Test
    public void getAll_SuccessTest() {
        when(gameRepository.findAll()).thenReturn(Collections.singletonList(new Game()));

        List<Game> all = gameService.getAll();

        assertNotNull(all);
        assertFalse(all.isEmpty());
    }

    @Test
    public void getAll_NotFoundTest() {
        when(gameRepository.findAll()).thenReturn(Collections.emptyList());

        List<Game> all = gameService.getAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    public void getAllByStatus_SuccessTest() {
        when(gameRepository.findAllByStatus(GameStatus.IN_PROGRESS)).thenReturn(Collections.singletonList(new Game()));

        List<Game> all = gameService.getAllByStatus(GameStatus.IN_PROGRESS);

        assertNotNull(all);
        assertFalse(all.isEmpty());
    }

    @Test
    public void getAllByStatus_NotFoundTest() {
        when(gameRepository.findAllByStatus(GameStatus.IN_PROGRESS)).thenReturn(Collections.emptyList());

        List<Game> all = gameService.getAllByStatus(GameStatus.IN_PROGRESS);

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    public void createNewGame_SuccessTest() {
        Game game = Game.builder()
                .id(1)
                .name("first")
                .type(GameType.ONE_VS_ONE)
                .status(GameStatus.DRAW)
                .build();
        when(gameRepository.save(any(Game.class))).thenReturn(game);

        Game newGame = gameService.createNewGame(new GameForm());

        assertNotNull(newGame);
        assertEquals(game.getId(), newGame.getId());
        assertEquals(game.getName(), newGame.getName());
        assertEquals(game.getType(), newGame.getType());
        assertEquals(game.getStatus(), newGame.getStatus());
    }

    @Test
    public void updateGameStatus_SuccessTest() {
        Game game = Game.builder()
                .id(1)
                .name("first")
                .type(GameType.ONE_VS_ONE)
                .status(GameStatus.X_WON)
                .build();
        when(gameRepository.save(any(Game.class))).thenReturn(game);


        Game newGame = gameService.updateGameStatus(Game.builder().status(GameStatus.IN_PROGRESS).build(), GameStatus.X_WON);

        assertNotNull(newGame);
        assertEquals(GameStatus.X_WON, newGame.getStatus());
    }
}
