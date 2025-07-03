package dev.codehouse.backend.admin.dto;

import lombok.Getter;

@Getter
public class AdminProblemRequest {
    private String title;
    private String problemNumber;
    private String url;
    private String difficulty;
    private String point;
    private String day;
}
