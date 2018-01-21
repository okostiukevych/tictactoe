package com.game.tictactoe.dto;

import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.Player;
import lombok.Data;

@Data
public class MoveDto {

    private int id;
    private int moveNumber;
    private int boardRowNumber;
    private int boardColumnNumber;
    private Player player;
    private GameDto game;
}
