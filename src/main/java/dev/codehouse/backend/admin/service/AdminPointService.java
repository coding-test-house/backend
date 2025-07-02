package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.admin.dto.UserPointResponse;
import dev.codehouse.backend.global.exception.AdminException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.domain.UserHistory;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminPointService {

    private final UserRepository userRepository;

    @Transactional
    public UserPointResponse adjustUserPoint(String username, int delta) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AdminException(ResponseCode.USER_NOT_FOUND));

        user.adjustPoint(delta);
        user.getHistories().add(UserHistory.adminAdjustment(username, delta));
        userRepository.save(user);

        return UserPointResponse.from(user);
    }
}
