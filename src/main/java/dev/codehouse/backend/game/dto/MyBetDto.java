package dev.codehouse.backend.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyBetDto {
    private String betType;
    private int betAmount;
}
