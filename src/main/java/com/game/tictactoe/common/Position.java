package com.game.tictactoe.common;

import com.game.tictactoe.entity.enums.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"boardRowNumber", "boardColumnNumber"})
public class Position {

    private int boardRowNumber;
    private int boardColumnNumber;
    private Player player;

    public Position(int boardRowNumber, int boardColumnNumber) {
        this(boardRowNumber, boardColumnNumber, null);
    }
}
