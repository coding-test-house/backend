package dev.codehouse.backend.user.service;

import dev.codehouse.backend.global.exception.UserException;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.HistoryType;
import dev.codehouse.backend.user.domain.UserHistory;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHistoryService {
    private final UserRepository userRepository;

    public void addUserHistory(String username, HistoryType type, String reason, int amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

        UserHistory userHistory = new UserHistory(LocalDateTime.now(), username, type, reason, amount);
        user.getHistories().add(userHistory);

        // 포인트 수정 코드
        // 필요시 추가 할 것
        // user.setPoint(user.getPoint() + amount);
        userRepository.save(user);
    }

    public List<UserHistory> getUserHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(ResponseCode.USER_NOT_FOUND));

        return user.getHistories();
    }
}
