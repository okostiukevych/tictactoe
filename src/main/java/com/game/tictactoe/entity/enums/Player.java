package com.game.tictactoe.entity.enums;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Objects;

public enum Player {
    PLAYER_1("Player1"),
    PLAYER_2("Player2"),
    COMPUTER("Computer");

    @Getter
    private String value;

    Player(String value) {
        this.value = value;
    }

    public Player getByValue(@NonNull String value) {
        return Arrays.stream(Player.values())
                .filter(player -> Objects.equals(player.getValue(), value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Player does not exist"));
    }
}
