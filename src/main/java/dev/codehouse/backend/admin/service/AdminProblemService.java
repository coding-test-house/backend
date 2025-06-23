package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.admin.dto.ProblemRequest;
import dev.codehouse.backend.admin.dto.ProblemResponse;
import dev.codehouse.backend.admin.entity.Problem;
import dev.codehouse.backend.admin.repository.ProblemRepository;
import dev.codehouse.backend.global.exception.AdminException;
import dev.codehouse.backend.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminProblemService {

    private final ProblemRepository problemRepository;

    //문제 등록
    public void saveProblem(ProblemRequest dto) {
        if (problemRepository.findByProblemNumber(dto.getProblemNumber()).isPresent()) {
            throw new AdminException(ResponseCode.PROBLEM_ALREADY_EXISTS);
        }

        Problem problem = Problem.createProblem(dto.getTitle(), dto.getProblemNumber(), dto.getUrl(), dto.getDay());
        problemRepository.save(problem);
    }

    //문제 삭제
    public void deleteProblem(String problemNumber) {
        Problem problem = problemRepository.findByProblemNumber(problemNumber)
                .orElseThrow(() -> new AdminException(ResponseCode.PROBLEM_NOT_FOUND));
        problemRepository.delete(problem);
    }

    //날짜로 문제 조회
    public List<ProblemResponse> getProblems(String day) {
        List<Problem> problems = problemRepository.findByDay(day);
        return problems.stream()
                .map(ProblemResponse::from)
                .toList();
    }

    public List<ProblemResponse> getAllProblems() {
        List<Problem> problems = problemRepository.findAll();
        return problems.stream()
                .map(ProblemResponse::from)
                .toList();
    }

    //전체 문제 조회 (페이징)
    public Page<ProblemResponse> getAllProblemsPaged(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        return problemRepository.findAll(pageable).map(ProblemResponse::from);
    }


}
