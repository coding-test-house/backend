package dev.codehouse.backend.user.service;

import dev.codehouse.backend.global.exception.UserException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.UserHistory;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHistoryService {

    private final UserRepository userRepository;

    public List<UserHistory> getUserHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));
        return user.getHistories();
    }
}
