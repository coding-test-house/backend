package dev.codehouse.backend.user.controller;

import dev.codehouse.backend.user.service.UserSolvedCheckerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserSolvedCheckerController {
    private final UserSolvedCheckerService userSolvedCheckerService;

    @PostMapping("{username}/solved/{problemNo}")
    public ResponseEntity<Void> markSolvedProblem(
            @PathVariable String username,
            @PathVariable String problemNo
    ) {
        userSolvedCheckerService.markUserSolvedProblem(username, problemNo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/solved/{problemNo}")
    public ResponseEntity<Boolean> checkSolvedProblem(
            @PathVariable String username,
            @PathVariable String problemNo
    ) {
        boolean solved = userSolvedCheckerService.isProblemSolved(username, problemNo);
        return ResponseEntity.ok(solved);
    }

    @GetMapping("/{username}/solved")
    public ResponseEntity<List<String>> getSolvedProblems(
            @PathVariable String username
    ) {
        List<String> problems = userSolvedCheckerService.getSolvedProblems(username);
        return ResponseEntity.ok(problems);
    }
}
