package com.game.tictactoe.service.impl;

import com.game.tictactoe.common.Position;
import com.game.tictactoe.entity.Game;
import com.game.tictactoe.entity.Move;
import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.Player;
import com.game.tictactoe.form.AutoMoveForm;
import com.game.tictactoe.form.MoveForm;
import com.game.tictactoe.repository.MoveRepository;
import com.game.tictactoe.service.MoveService;
import com.game.tictactoe.service.helper.GameHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class MoveServiceImpl implements MoveService {

    private final MoveRepository moveRepository;

    @Autowired
    public MoveServiceImpl(MoveRepository moveRepository) {
        this.moveRepository = moveRepository;
    }

    @Override
    public Move createNewMove(Game game, MoveForm form) {
        return moveRepository.save(Move.builder()
                .boardRowNumber(form.getBoardRowNumber())
                .boardColumnNumber(form.getBoardColumnNumber())
                .moveNumber(form.getMoveNumber())
                .game(game)
                .build());
    }

    @Override
    public Move autoCreateMove(Game game, AutoMoveForm form) {
        Position position = GameHelper.nextAutoMove(getTakenMovePositionsInGame(game));
        return moveRepository.save(Move.builder()
                .boardRowNumber(position.getBoardRowNumber())
                .boardColumnNumber(position.getBoardColumnNumber())
                .moveNumber(form.getMoveNumber())
                .game(game)
                .build());
    }

    @Override
    public GameStatus checkCurrentGameStatus(Game game) {
        if (GameHelper.isWinner(getPlayerMovePositionsInGame(game, Player.PLAYER_1))) {
            return GameStatus.X_WON;
        } else if (GameHelper.isWinner(getPlayerMovePositionsInGame(game, Player.PLAYER_2))) {
            return GameStatus.O_WON;
        } else if (GameHelper.isWinner(getPlayerMovePositionsInGame(game, Player.COMPUTER))) {
            return GameStatus.O_WON;
        } else if (GameHelper.boardIsFullFilled(getTakenMovePositionsInGame(game))) {
            return GameStatus.DRAW;
        } else {
            return GameStatus.IN_PROGRESS;
        }
    }

    @Override
    public List<Move> getMovesInGame(Game game) {
        return moveRepository.findAllByGame(game);
    }

    @Override
    public List<Position> getTakenMovePositionsInGame(Game game) {
        return moveRepository.findAllByGame(game).stream()
                .map(move -> new Position(move.getBoardRowNumber(), move.getBoardColumnNumber(), move.getPlayer()))
                .collect(toList());
    }

    @Override
    public List<Position> getPlayerMovePositionsInGame(Game game, Player player) {
        List<Move> moves = moveRepository.findAllByGameAndPlayer(game, player);
        return moves.stream()
                .map(move -> new Position(move.getBoardRowNumber(), move.getBoardColumnNumber()))
                .collect(toList());
    }
}
