package dev.codehouse.backend.user.service;

import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.dto.RankingResponse;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class UserRankingService {

    private final UserRepository userRepository;

    public List<RankingResponse> getTopRanking(){
        List<User> topUsers = userRepository.findAllByOrderByPointDesc(
                PageRequest.of(0,5)
        );
        return RankingResponse.from(topUsers);
    }

    public List<RankingResponse> getClassRanking(String className) {
        List<User> classUsers = userRepository.findByClassesOrderByPointDesc(className, PageRequest.of(0,5));
        return RankingResponse.from(classUsers);
    }
}
