package dev.codehouse.backend.admin.dto;

import dev.codehouse.backend.admin.entity.Problem;
import dev.codehouse.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ProblemResponse {
    private String title;
    private String problemNumber;
    private String url;
    private String day;

    public static ProblemResponse from(Problem problem) {
        return new ProblemResponse(
                problem.getTitle(),
                problem.getProblemNumber(),
                problem.getUrl(),
                problem.getDay()
        );
    }
}
