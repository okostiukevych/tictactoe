package com.game.tictactoe.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoveForm {

    @NonNull
    private int boardRowNumber;
    @NonNull
    private int boardColumnNumber;
    @NotNull
    private int moveNumber;
}
