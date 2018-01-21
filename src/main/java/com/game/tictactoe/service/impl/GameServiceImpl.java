package com.game.tictactoe.service.impl;

import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.form.GameForm;
import com.game.tictactoe.repository.GameRepository;
import com.game.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Game getById(int gameId) {
        return gameRepository.findOne(gameId);
    }

    @Transactional(readOnly = true)
    @Override
    public Game getByName(String name) {
        return gameRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Game> getAll() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Game> getAllByStatus(GameStatus status) {
        return gameRepository.findAllByStatus(status);
    }

    @Override
    public Game createNewGame(GameForm form) {
        Game game = new Game();
        game.setName(form.getName());
        game.setType(form.getType());
        return gameRepository.save(game);
    }

    @Override
    public Game updateGameStatus(Game game, GameStatus status) {
        game.setStatus(status);
        return gameRepository.save(game);
    }
}
