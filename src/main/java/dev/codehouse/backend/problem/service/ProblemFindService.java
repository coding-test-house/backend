package dev.codehouse.backend.problem.service;

import dev.codehouse.backend.global.exception.UserException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.problem.repository.ProblemRepository;
import dev.codehouse.backend.problem.dto.ProblemResponse;
import dev.codehouse.backend.problem.domain.Problem;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProblemFindService {

    private final ProblemRepository problemRepository;
    private final UserRepository userRepository;

    public List<ProblemResponse> getProblems(String day, String username) {
        List<Problem> problems = problemRepository.findByDay(day);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserException(ResponseCode.USER_NOT_FOUND)
        );
        Set<String> solvedProblems = new HashSet<>(user.getSolvedProblems());

        return problems.stream()
                .map(problem -> ProblemResponse.from(
                        problem,
                        solvedProblems.contains(problem.getProblemNumber())
                ))
                .toList();
    }
}
