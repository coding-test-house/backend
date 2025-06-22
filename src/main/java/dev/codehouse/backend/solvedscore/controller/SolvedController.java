package dev.codehouse.backend.solvedscore.controller;

import dev.codehouse.backend.solvedscore.service.SolvedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("solvedCheck")
@RequiredArgsConstructor
public class SolvedController {
    private final SolvedService solvedService;

    @GetMapping("/check")
    public boolean checkSolved(
            @RequestParam("user") String userId,
            @RequestParam("problem") int problemId) {
        return solvedService.hasUserSolvedProblem(userId, problemId);
    }

    @GetMapping("/list")
    public List<Integer> getProblemList(
            @RequestParam("user") String userId,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        return solvedService.getProblemList(userId, size);
    }
}
