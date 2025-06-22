package dev.codehouse.backend.admin.controller;

import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRankingController {
    private final UserService userService;

    @GetMapping("/ranking")
    public List<User> getUserRanking() {
        return userService.getUserSortedByPoint();
    }
}
