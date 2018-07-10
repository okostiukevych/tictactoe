package com.game.tictactoe.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveForm {

    private int boardRowNumber;
    private int boardColumnNumber;
    private int moveNumber;
}
