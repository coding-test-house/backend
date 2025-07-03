package dev.codehouse.backend.admin.dto;

import dev.codehouse.backend.problem.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdminProblemResponse {
    private String title;
    private String problemNumber;
    private String url;
    private String difficulty;
    private int point;
    private String day;

    public static AdminProblemResponse from(Problem problem) {
        return new AdminProblemResponse(
                problem.getTitle(),
                problem.getProblemNumber(),
                problem.getUrl(),
                problem.getDifficulty(),
                problem.getPoint(),
                problem.getDay()
        );
    }
}
