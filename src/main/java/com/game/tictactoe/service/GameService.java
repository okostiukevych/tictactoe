package com.game.tictactoe.service;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.form.GameForm;

import java.util.List;

public interface GameService {

    Game getById(int gameId);
    Game getByName(String name);
    List<Game> getAll();
    List<Game> getAllByStatus(GameStatus status);

    Game createNewGame(GameForm form);
    Game updateGameStatus(Game game, GameStatus status);
}
