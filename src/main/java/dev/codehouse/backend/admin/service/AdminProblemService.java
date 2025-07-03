package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.admin.dto.AdminProblemRequest;
import dev.codehouse.backend.admin.dto.AdminProblemResponse;
import dev.codehouse.backend.problem.domain.Problem;
import dev.codehouse.backend.problem.repository.ProblemRepository;
import dev.codehouse.backend.global.exception.AdminException;
import dev.codehouse.backend.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminProblemService {

    private final ProblemRepository problemRepository;

    //문제 등록
    public void saveProblem(AdminProblemRequest dto) {
        if (problemRepository.findByProblemNumber(dto.getProblemNumber()).isPresent()) {
            throw new AdminException(ResponseCode.PROBLEM_ALREADY_EXISTS);
        }

        Problem problem = Problem.createProblem(dto.getTitle(), dto.getProblemNumber(), dto.getUrl(), dto.getDifficulty(), calculatePoint(dto.getDifficulty()), dto.getDay());
        problemRepository.save(problem);
    }

    //문제 삭제
    public void deleteProblem(String problemNumber) {
        Problem problem = problemRepository.findByProblemNumber(problemNumber)
                .orElseThrow(() -> new AdminException(ResponseCode.PROBLEM_NOT_FOUND));
        problemRepository.delete(problem);
    }

    //날짜로 문제 조회
    public List<AdminProblemResponse> getProblems(String day) {
        List<Problem> problems = problemRepository.findByDay(day);
        return problems.stream()
                .map(AdminProblemResponse::from)
                .toList();
    }

    public List<AdminProblemResponse> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        return problems.stream()
                .map(AdminProblemResponse::from)
                .toList();
    }

    //티어로 point 계산
    private int calculatePoint(String difficulty) {
        if (difficulty == null || difficulty.isEmpty()) {
            return 0;
        }
        Map<String, Integer> basePointMap = Map.of(
                "B", 50,
                "S", 300,
                "G", 600,
                "P", 1000
        );
        try {
            String tier = difficulty.substring(0, 1);
            int level = Integer.parseInt(difficulty.substring(1));
            int basePoint = basePointMap.getOrDefault(tier, 0);
            return basePoint + (5 - level) * 50;
        } catch (Exception e) {
            return 0;
        }
    }
}
