package com.game.tictactoe.service.impl;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.form.GameForm;
import com.game.tictactoe.repository.GameRepository;
import com.game.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game getById(int gameId) {
        return gameRepository.findOne(gameId);
    }

    @Override
    public Game getByName(String name) {
        return gameRepository.findByName(name);
    }

    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> getAllByStatus(GameStatus status) {
        return gameRepository.findAllByStatus(status);
    }

    @Override
    public Game createNewGame(GameForm form) {
        return gameRepository.save(Game.builder()
                .name(form.getName())
                .type(form.getType())
                .build());
    }

    @Override
    public Game updateGameStatus(Game game, GameStatus status) {
        return gameRepository.save(game.toBuilder()
                .status(status)
                .build());
    }
}
