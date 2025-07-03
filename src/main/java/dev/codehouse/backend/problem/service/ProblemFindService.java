package dev.codehouse.backend.problem.service;

import dev.codehouse.backend.admin.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemFindService {

    private final ProblemRepository problemRepository;


}
