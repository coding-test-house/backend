package dev.codehouse.backend.problem.repository;

import dev.codehouse.backend.problem.domain.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {
    List<Problem> findByDay(String day);
    Optional<Problem> findByProblemNumber(String problemNumber);
}
