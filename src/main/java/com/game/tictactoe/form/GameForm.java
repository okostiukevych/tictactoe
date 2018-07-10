package com.game.tictactoe.form;

import com.game.tictactoe.entity.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameForm {

    @NotEmpty
    @Size(max = 10)
    private String name;
    @NotNull
    private GameType type;
}
