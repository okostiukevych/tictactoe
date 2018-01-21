package com.game.tictactoe.form;

import com.game.tictactoe.entity.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameForm {

    @NotEmpty
    @Max(10)
    private String name;
    @NotNull
    private GameType type;
}
