package dev.codehouse.backend.user.service;

import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSolvedCheckerService {

    private final UserRepository userRepository;

    public void markUserSolvedProblem(String username, String problemNo) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));

        if (user.hasSolvedProblem(problemNo)) {
            throw new IllegalStateException("이미 푼 문제입니다");
        }
        user.addSolvedProblem(problemNo);
        userRepository.save(user);
    }

    public boolean isProblemSolved(String username, String problemNo) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));

        return user.hasSolvedProblem(problemNo);
    }

    public List<String> getSolvedProblems(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다"));

        return user.getSolvedProblems();
    }
}
