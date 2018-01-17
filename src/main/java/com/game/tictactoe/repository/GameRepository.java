package com.game.tictactoe.repository;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    List<Game> findAllByStatus(GameStatus status);
}
