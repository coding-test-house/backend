package dev.codehouse.backend.solvedscore.service;

import dev.codehouse.backend.solvedscore.client.SolvedClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolvedService {
    private final SolvedClient solvedClient;

    public boolean hasUserSolvedProblem(String userId, int problemId) {
        return solvedClient.checkSolved(userId, problemId);
    }

    public List<Integer> getProblemList(String userId, int size) {
        return solvedClient.getProblemIds(userId, size);
    }
}
