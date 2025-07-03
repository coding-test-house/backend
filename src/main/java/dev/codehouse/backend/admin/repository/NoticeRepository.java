package dev.codehouse.backend.admin.repository;

import dev.codehouse.backend.admin.domain.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends MongoRepository<Notice, String> {
}
