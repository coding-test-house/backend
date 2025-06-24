package dev.codehouse.backend.user.service;

import dev.codehouse.backend.user.domain.History;
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

    public void addHistory(String username, String type, String reason, int amount) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        History history = new History(LocalDateTime.now(), username, type, reason, amount);
        user.getHistories().add(history);

        // 포인트 수정 코드
        // 필요시 추가 할 것
        // user.setPoint(user.getPoint() + amount);
        userRepository.save(user);
    }

    public List<History> getUserHistory(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return user.getHistories();
    }
}
