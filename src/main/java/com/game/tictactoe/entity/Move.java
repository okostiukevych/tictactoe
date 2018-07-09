package com.game.tictactoe.entity;

import com.game.tictactoe.entity.enums.GameType;
import com.game.tictactoe.entity.enums.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Move")
public class Move {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;
    @Column(name = "move_number", nullable = false)
    private int moveNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "player", nullable = false)
    private Player player;
    @Column(name = "row_number", nullable = false)
    private int boardRowNumber;
    @Column(name = "column_number", nullable = false)
    private int boardColumnNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @PrePersist
    private void init() {
        player = moveNumber % 2 != 0
                ? Player.PLAYER_1
                : GameType.ONE_VS_ONE.equals(game.getType())
                        ? Player.PLAYER_2
                        : Player.COMPUTER;
    }
}
