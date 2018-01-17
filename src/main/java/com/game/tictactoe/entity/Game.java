package com.game.tictactoe.entity;

import com.game.tictactoe.entity.enums.GameStatus;
import com.game.tictactoe.entity.enums.GameType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "name", nullable = false, length = 10)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GameStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private GameType type;

    @PrePersist
    private void init() {
        status = GameStatus.IN_PROGRESS;
    }
}
