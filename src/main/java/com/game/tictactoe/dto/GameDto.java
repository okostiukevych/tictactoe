package com.game.tictactoe.dto;

import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.GameType;
import lombok.Data;

@Data
public class GameDto {

    private int id;
    private String name;
    private GameStatus status;
    private GameType type;
}
