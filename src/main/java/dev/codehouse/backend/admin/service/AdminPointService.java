package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.admin.dto.UserPointResponse;
import dev.codehouse.backend.global.exception.AdminException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminPointService {

    private final UserRepository userRepository;

    public UserPointResponse adjustUserPoint(String userId, int delta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AdminException(ResponseCode.USER_NOT_FOUND));
        user.adjustPoint(delta);
        userRepository.save(user);

        return UserPointResponse.from(user);
    }
}
