package dev.codehouse.backend.problem.controller;

import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.problem.dto.ProblemResponse;
import dev.codehouse.backend.problem.dto.ProblemSubmitRequest;
import dev.codehouse.backend.problem.service.ProblemCheckService;
import dev.codehouse.backend.problem.service.ProblemFindService;
import dev.codehouse.backend.problem.service.ProblemSubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problem")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemSubmitService problemSubmitService;
    private final ProblemFindService problemFindService;

    @PostMapping("/check")
    public ResponseEntity<ApiResponse<Void>> submitProblems(@RequestBody ProblemSubmitRequest request, Authentication authentication) {
        problemSubmitService.submitSolvedProblem(authentication.getName(), request.getProblemNumber(), request.getPoint());
        return ApiResponseFactory.success(ResponseCode.PROBLEM_SOLVED);
    }

    @GetMapping("/{today}")
    public ResponseEntity<ApiResponse<List<ProblemResponse>>> getProblems(@PathVariable String today, Authentication authentication) {
        return ApiResponseFactory.success(ResponseCode.PROBLEM_FOUND, problemFindService.getProblems(today, authentication.getName()));
    }
}
