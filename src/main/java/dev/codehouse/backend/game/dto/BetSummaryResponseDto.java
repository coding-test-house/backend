package dev.codehouse.backend.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BetSummaryResponseDto {
    private String round;
    private SideSummary even;
    private SideSummary odd;
    private MyBetDto myBet;
    private String resultType;

    @Getter
    @AllArgsConstructor
    public static class SideSummary {
        private int totalBet;
        private List<TopBettor> topBettors;
    }

    @Getter
    @AllArgsConstructor
    public static class TopBettor {
        private String username;
        private int amount;
    }
}
