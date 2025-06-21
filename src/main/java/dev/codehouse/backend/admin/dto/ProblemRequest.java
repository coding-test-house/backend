package dev.codehouse.backend.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProblemRequest {
    private String title;
    private String problemNumber;
    private String url;
    private String day;
}
