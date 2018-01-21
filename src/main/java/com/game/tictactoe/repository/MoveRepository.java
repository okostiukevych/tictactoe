package com.game.tictactoe.repository;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Integer> {

    List<Move> findAllByGame(Game game);
    List<Move> findAllByGameAndPlayer(Game game, Player player);
    int countByGameAndPlayer(Game game, Player player);
}
