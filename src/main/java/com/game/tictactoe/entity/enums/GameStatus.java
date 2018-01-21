package com.game.tictactoe.entity.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Arrays;
import java.util.Objects;

public enum GameStatus {
    IN_PROGRESS("In progress"),
    X_WON("X won"),
    O_WON("O won"),
    DRAW("Draw");

    @Getter
    private String value;

    GameStatus(String value) {
        this.value = value;
    }

    public GameStatus getByValue(@NonNull String value) {
        return Arrays.stream(GameStatus.values())
                .filter(gameStatus -> Objects.equals(gameStatus.getValue(), value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Status does not exist"));
    }

    @Override
    public String toString() {
        return value;
    }
}
