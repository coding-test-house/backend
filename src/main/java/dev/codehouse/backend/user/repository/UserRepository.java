package dev.codehouse.backend.user.repository;

import dev.codehouse.backend.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, String id);

    boolean existsByUsernameAndClasses(String username, String classes);

    boolean existsByClasses(String classes);

    void deleteByUsername(String username);

    void deleteAllByClasses(String classes);

    void deleteAllByUsernameAndClasses(String username, String classes);

    void deleteAllByClassesAndUsername(String classes, String username);
}
