package com.game.tictactoe.service;

import com.game.tictactoe.common.Position;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.Player;
import com.game.tictactoe.form.AutoMoveForm;
import com.game.tictactoe.form.MoveForm;

import java.util.List;

public interface MoveService {

    Move createNewMove(Game game, MoveForm form);
    Move autoCreateMove(Game game, AutoMoveForm form);
    GameStatus checkCurrentGameStatus(Game game);
    List<Move> getMovesInGame(Game game);
    List<Position> getTakenMovePositionsInGame(Game game);
    List<Position> getPlayerMovePositionsInGame(Game game, Player player);
}
