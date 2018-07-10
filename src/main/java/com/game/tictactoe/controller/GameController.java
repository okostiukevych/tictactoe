package com.game.tictactoe.controller;

import com.game.tictactoe.dto.GameDto;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.form.GameForm;
import com.game.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final ModelMapper modelMapper;

    @Autowired
    public GameController(GameService gameService,
                          ModelMapper modelMapper) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<GameDto> createNewGame(@RequestBody GameForm form,
                                                 HttpSession session) {
        Game newGame = gameService.createNewGame(form);
        session.setAttribute("gameId", newGame.getId());
        log.info("Game [id={}] stored in session", newGame.getId());
        return ResponseEntity.ok(modelMapper.map(newGame, GameDto.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable int id,
                                               HttpSession session) {
        Game byId = gameService.getById(id);
        session.setAttribute("gameId", id);
        log.info("Game [id={}] stored in session", id);
        return ResponseEntity.ok(modelMapper.map(byId, GameDto.class));
    }

    @PostMapping("/join")
    public ResponseEntity<GameDto> joinGame(@RequestBody GameForm form,
                                            HttpSession session) {
        Game byId = gameService.getByName(form.getName());
        session.setAttribute("gameId", byId.getId());
        log.info("Game [id={}] stored in session", byId.getId());
        return ResponseEntity.ok(modelMapper.map(byId, GameDto.class));
    }

    @GetMapping("/list")
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<Game> games = gameService.getAll();
        log.info("Size of all games is {}", games.size());
        return ResponseEntity.ok(modelMapper.map(games, new TypeToken<List<GameDto>>() {
        }.getType()));
    }
}
