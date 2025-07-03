package dev.codehouse.backend.problem.service;

import dev.codehouse.backend.global.exception.ProblemException;
import dev.codehouse.backend.global.exception.UserException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.problem.domain.Problem;
import dev.codehouse.backend.problem.repository.ProblemRepository;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.domain.UserHistory;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static dev.codehouse.backend.global.response.ResponseCode.PROBLEM_NOT_FOUND;
import static dev.codehouse.backend.global.response.ResponseCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProblemSubmitService {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final ProblemCheckService problemCheckService;

    @Transactional
    public void submitSolvedProblem(String username, String problemNo, int point) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        Problem problem = problemRepository.findByProblemNumber(problemNo)
                .orElseThrow(() -> new ProblemException(PROBLEM_NOT_FOUND));

        String today = LocalDate.now().toString();
        if (!problem.getDay().equals(today)) {
            throw new ProblemException(ResponseCode.PROBLEM_NOT_TODAY);
        }

        if (user.hasSolvedProblem(problemNo)) {
            throw new ProblemException(ResponseCode.PROBLEM_ALREADY_SOLVED);
        }

        boolean isSolved = problemCheckService.checkSolvedProblem(username, Integer.parseInt(problemNo));
        if (!isSolved) {
            throw new ProblemException(ResponseCode.PROBLEM_NOT_SOLVED);
        }

        user.addSolvedProblem(problemNo);
        user.adjustPoint(point);

        String reason = "문제" + problemNo + "번 풀어서 적립";
        user.getHistories().add(UserHistory.problemSolved(username, reason, point));
        userRepository.save(user);
    }
}
