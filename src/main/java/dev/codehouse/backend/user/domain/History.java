package dev.codehouse.backend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class History {
    private LocalDateTime time;  // 시간
    private String type;  // 유형
    private String reason;  // 내용
    private int amount;  // 변동
}
