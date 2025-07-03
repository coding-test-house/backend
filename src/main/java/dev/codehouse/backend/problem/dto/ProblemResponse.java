package dev.codehouse.backend.problem.dto;

import dev.codehouse.backend.problem.domain.Problem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProblemResponse {
    private String title;
    private String problemNumber;
    private String url;
    private String difficulty;
    private int points;
    private boolean solved;

    public static ProblemResponse from(Problem problem, boolean solved) {
        return new ProblemResponse(
                problem.getTitle(),
                problem.getProblemNumber(),
                problem.getUrl(),
                problem.getDifficulty(),
                problem.getPoint(),
                solved
        );
    }
}
