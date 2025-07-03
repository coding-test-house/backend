package dev.codehouse.backend.problem.dto;

import lombok.Getter;

@Getter
public class ProblemSubmitRequest {

    private String problemNumber;
    private int point;
}
