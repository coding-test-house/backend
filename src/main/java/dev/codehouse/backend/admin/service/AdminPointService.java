package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.admin.dto.UserPointResponse;
import dev.codehouse.backend.global.exception.AdminException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminPointService {

    private final UserRepository userRepository;

    public UserPointResponse adjustUserPoint(String userId, int delta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AdminException(ResponseCode.USER_NOT_FOUND));
        user.adjustPoint(delta);
        userRepository.save(user);

        log.info("관리자 포인트 조정: userId={}, delta={}", userId, delta);
        return UserPointResponse.from(user);
    }
}
