package com.game.tictactoe.dto;

import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.GameType;
import com.game.tictactoe.form.GameForm;
import lombok.Data;

@Data
public class GameDto {

    private int id;
    private String name;
    private GameStatus status;
    private GameType type;

    public GameForm toGameForm() {
        return GameForm.builder()
                .name(name)
                .type(type)
                .build();
    }
}
