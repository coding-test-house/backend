package dev.codehouse.backend.user.repository;

import dev.codehouse.backend.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsById(String id);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, String id);

    boolean existsByUsernameAndClasses(String username, String classes);

    boolean existsByClasses(String classes);

    void deleteByUsername(String username);

    void deleteAllByClasses(String classes);

    void deleteAllByUsernameAndClasses(String username, String classes);

    void deleteAllByClassesAndUsername(String classes, String username);

    List<User> findAllByOrderByPointDesc(org.springframework.data.domain.Pageable pageable);

    long countByPointGreaterThan(int pointIsGreaterThan);

    long countByClassesAndPointGreaterThan(String classes, int point);

    List<User> findByClassesOrderByPointDesc(String classes, Pageable pageable);
}
