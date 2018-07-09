package com.game.tictactoe.repository;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Integer> {

    @Transactional(readOnly = true)
    Game findByName(String name);
    @Transactional(readOnly = true)
    List<Game> findAllByStatus(GameStatus status);
}
