package dev.codehouse.backend.game.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Bet {
    private String username;
    private int betAmount;
    private String betType; // "odd" or "even"
}