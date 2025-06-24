package dev.codehouse.backend.admin.repository;

import dev.codehouse.backend.admin.entity.Notice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends MongoRepository<Notice, String> {
}
