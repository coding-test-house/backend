package dev.codehouse.backend.problem.controller;

import dev.codehouse.backend.problem.service.ProblemCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solvedCheck")
@RequiredArgsConstructor
public class ProblemController {
    private final ProblemCheckService problemCheckService;

    @GetMapping("/check")
    public boolean checkSolved(
            @RequestParam("user") String userId,
            @RequestParam("problem") int problemId) {
        return problemCheckService.hasUserSolvedProblem(userId, problemId);
    }

    @GetMapping("/list")
    public List<Integer> getProblemList(
            @RequestParam("user") String userId,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return problemCheckService.getProblemList(userId, size);
    }
}
