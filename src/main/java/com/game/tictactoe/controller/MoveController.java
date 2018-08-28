package com.game.tictactoe.controller;

import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.form.AutoMoveForm;
import com.game.tictactoe.form.MoveForm;
import com.game.tictactoe.service.GameService;
import com.game.tictactoe.service.MoveService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/move")
public class MoveController {

    private final MoveService moveService;
    private final GameService gameService;
    private final ModelMapper modelMapper;

    @Autowired
    public MoveController(MoveService moveService,
                          GameService gameService,
                          ModelMapper modelMapper) {
        this.moveService = moveService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<MoveDto> createNewMove(@RequestBody MoveForm form,
                                                 HttpSession session) {
        int gameId = (int) session.getAttribute("gameId");
        Game byId = gameService.getById(gameId);

        Move newMove = moveService.createNewMove(byId, form);
        log.info("New Move[{},{}]", newMove.getBoardRowNumber(), newMove.getBoardColumnNumber());

        GameStatus status = moveService.checkCurrentGameStatus(byId);
        gameService.updateGameStatus(byId, status);
        return ResponseEntity.ok(modelMapper.map(newMove, MoveDto.class));
    }

    @PostMapping("/autoCreate")
    public ResponseEntity<MoveDto> autoCreateMove(@RequestBody AutoMoveForm form,
                                                  HttpSession session) {
        int gameId = (int) session.getAttribute("gameId");
        Game byId = gameService.getById(gameId);

        Move newAutoMove = moveService.autoCreateMove(byId, form);
        log.info("New Auto Move[{},{}]", newAutoMove.getBoardRowNumber(), newAutoMove.getBoardColumnNumber());

        GameStatus status = moveService.checkCurrentGameStatus(byId);
        gameService.updateGameStatus(byId, status);
        return ResponseEntity.ok(modelMapper.map(newAutoMove, MoveDto.class));
    }

    @GetMapping("/list")
    public ResponseEntity<List<MoveDto>> getMovesInGame(HttpSession session) {
        int gameId = (int) session.getAttribute("gameId");
        Game byId = gameService.getById(gameId);
        List<Move> movesInGame = moveService.getMovesInGame(byId);
        log.info("Size of all moves is {} (game: {})", movesInGame.size(), gameId);
        return ResponseEntity.ok(modelMapper.map(movesInGame, new TypeToken<List<MoveDto>>() {
        }.getType()));
    }
}
