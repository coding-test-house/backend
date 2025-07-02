package dev.codehouse.backend.user.dto;

import dev.codehouse.backend.user.domain.HistoryType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryRequest {
    private String username;
    private HistoryType type;
    private String reason;
    private int amount;
}
