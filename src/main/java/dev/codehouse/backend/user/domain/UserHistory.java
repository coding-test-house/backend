package dev.codehouse.backend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
    private LocalDateTime time;  // 시간
    private String username;
    private HistoryType type;  // 유형
    private String reason;  // 내용
    private int amount;

    public static UserHistory gameWin(String username, String reason, int amount) {
        return new UserHistory(LocalDateTime.now(), username, HistoryType.GAME_WIN, reason, amount);
    }

    public static UserHistory gameLoss(String username, String reason, int amount) {
        return new UserHistory(LocalDateTime.now(), username, HistoryType.GAME_LOSS, reason, amount);
    }

    public static UserHistory problemSolved(String username, String reason, int amount) {
        return new UserHistory(LocalDateTime.now(), username, HistoryType.PROBLEM_SOLVED, reason, amount);
    }

    public static UserHistory betting(String username, String reason, int amount) {
        return new UserHistory(LocalDateTime.now(), username, HistoryType.BETTING, reason, amount);
    }

    public static UserHistory adminAdjustment(String username, int amount) {
        return new UserHistory(LocalDateTime.now(), username, HistoryType.ADMIN_ADJUSTMENT, amount > 0 ? "관리자 지급" : "관리자 차감", amount);
    }
}
