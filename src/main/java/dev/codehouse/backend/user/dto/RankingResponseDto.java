package dev.codehouse.backend.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingResponseDto {
    private String username;
    private int point;
    private int rank;
    private String classes;
}
