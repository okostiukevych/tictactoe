package com.game.tictactoe.controller;

import com.game.tictactoe.dto.GameDto;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.form.GameForm;
import com.game.tictactoe.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;
    private final ModelMapper modelMapper;
    private final HttpSession session;

    @Autowired
    public GameController(GameService gameService,
                          ModelMapper modelMapper,
                          HttpSession session) {
        this.gameService = gameService;
        this.modelMapper = modelMapper;
        this.session = session;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<GameDto> createNewGame(@RequestBody GameForm form) {
        Game newGame = gameService.createNewGame(form);
        session.setAttribute("gameId", newGame.getId());
        log.info("Game [id={}] stored in session", newGame.getId());
        return ResponseEntity.ok(modelMapper.map(newGame, GameDto.class));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable int id) {
        Game byId = gameService.getById(id);
        session.setAttribute("gameId", id);
        log.info("Game [id={}] stored in session", id);
        return ResponseEntity.ok(modelMapper.map(byId, GameDto.class));
    }
}
