package com.game.tictactoe.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    private int boardRowNumber;
    private int boardColumnNumber;
}
