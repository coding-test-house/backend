package dev.codehouse.backend.user.service;

import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.dto.RankingResponseDto;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRankingService {

    private final UserRepository userRepository;

    /**
     * 전체 사용자 중 상위 5명 랭킹 조회
     */
    public List<RankingResponseDto> getTopRanking(){
        log.info("전체 랭킹 조회 시작 - 상위 5명");

        List<User> topUsers = userRepository.findAllByOrderByPointDesc(
                PageRequest.of(0,5)
        );

        return convertToRankingDto(topUsers);
    }

    /**
     * 특정 회차별 상위 5명 랭킹 조회
     */
    public List<RankingResponseDto> getclassRanking(String className) {
        log.info("회차별 랭킹 조회 시작 - 회차 : {}, 상위 5명", className);
        List<User> classUsers = userRepository.findByClassesOrderByPointDesc(className, PageRequest.of(0,5));

        return convertToRankingDto(classUsers);
    }

    /**
     * 특정 사용자의 전체 등수 조회
     */
    public int getUserOverallRank(String username) {
        log.info("사용자 전체 등수 조회 - username={}", username);
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            long higherRankedCount = userRepository.countByPointGreaterThan(user.get().getPoint());
            return (int)higherRankedCount + 1;
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: ");
        }
    }

    /**
     * 특정 사용자의 회차별 등수 조회
     */
    public int getUserClassRank(String username) {
        log.info("사용자 회차별 등수 조회 - username: {}, ", username);

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            String userClasses = user.get().getClasses();
            long higherRankedCount = userRepository.countByClassesAndPointGreaterThan(user.get().getClasses(), user.get().getPoint());
            return (int)higherRankedCount + 1;
        }
        else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    private List<RankingResponseDto> convertToRankingDto(List<User> users) {
        AtomicInteger rank = new AtomicInteger(1);

        return users.stream()
                .map(user -> RankingResponseDto.builder()
                        .username(user.getUsername())
                        .point(user.getPoint())
                        .rank(rank.getAndIncrement())
                        .classes(user.getClasses())
                        .build())
                .toList();
    }
}
