package com.game.tictactoe.repository;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MoveRepository extends JpaRepository<Move, Integer> {

    @Transactional(readOnly = true)
    List<Move> findAllByGame(Game game);
    @Transactional(readOnly = true)
    List<Move> findAllByGameAndPlayer(Game game, Player player);
}
