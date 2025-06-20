package dev.codehouse.backend.game.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OddEvenRequestDto {
    private String username;
    private int betAmount;
    private String betType; //홀 또는 짝 (odd or even)
}
