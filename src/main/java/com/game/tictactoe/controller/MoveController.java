package com.game.tictactoe.controller;

import com.game.tictactoe.dto.MoveDto;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.Player;
import com.game.tictactoe.form.AutoMoveForm;
import com.game.tictactoe.form.MoveForm;
import com.game.tictactoe.service.GameService;
import com.game.tictactoe.service.MoveService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/move")
public class MoveController {

    private final MoveService moveService;
    private final GameService gameService;
    private final ModelMapper modelMapper;
    private final HttpSession session;

    @Autowired
    public MoveController(MoveService moveService,
                          GameService gameService,
                          ModelMapper modelMapper, HttpSession session) {
        this.moveService = moveService;
        this.gameService = gameService;
        this.modelMapper = modelMapper;
        this.session = session;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<MoveDto> createNewMove(@RequestBody MoveForm form) {
        int gameId = (int) session.getAttribute("gameId");
        Game byId = gameService.getById(gameId);

        Move newMove = moveService.createNewMove(byId, form);
        log.info("New Move[{},{}]", newMove.getBoardRowNumber(), newMove.getBoardColumnNumber());

        GameStatus status = moveService.checkCurrentGameStatus(byId);
        gameService.updateGameStatus(byId, status);
        return ResponseEntity.ok(modelMapper.map(newMove, MoveDto.class));
    }

    @PostMapping(value = "/autoCreate")
    public ResponseEntity<MoveDto> autoCreateMove(@RequestBody AutoMoveForm form) {
        int gameId = (int) session.getAttribute("gameId");
        Game byId = gameService.getById(gameId);

        Move newAutoMove = moveService.autoCreateMove(byId, form);
        log.info("New Auto Move[{},{}]", newAutoMove.getBoardRowNumber(), newAutoMove.getBoardColumnNumber());

        GameStatus status = moveService.checkCurrentGameStatus(byId);
        gameService.updateGameStatus(byId, status);
        return ResponseEntity.ok(modelMapper.map(newAutoMove, MoveDto.class));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<MoveDto>> getMovesInGame() {
        int gameId = (int) session.getAttribute("gameId");
        Game byId = gameService.getById(gameId);
        return ResponseEntity.ok(modelMapper.map(moveService.getMovesInGame(byId), new TypeToken<List<MoveDto>>() {}.getType()));
    }
}
