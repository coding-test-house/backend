package dev.codehouse.backend.admin.dto;

import lombok.Getter;

@Getter
public class UserPointRequest {
    String userId;
    int delta;
}
