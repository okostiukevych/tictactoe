package com.game.tictactoe.entity.enums;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Objects;

public enum GameType {
    ONE_VS_ONE("One vs One"),
    ONE_VS_COMPUTER("One vs Computer");

    @Getter
    private String value;

    GameType(String value) {
        this.value = value;
    }

    public GameType getByValue(@NonNull String value) {
        return Arrays.stream(GameType.values())
                .filter(gameType -> Objects.equals(gameType.getValue(), value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Status does not exist"));
    }

    @Override
    public String toString() {
        return value;
    }
}
