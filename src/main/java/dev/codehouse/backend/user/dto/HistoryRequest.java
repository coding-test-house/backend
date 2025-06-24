package dev.codehouse.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryRequest {
    private String username;
    private String type;
    private String reason;
    private int amount;
}
