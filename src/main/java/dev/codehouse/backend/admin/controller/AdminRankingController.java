package dev.codehouse.backend.admin.controller;

import dev.codehouse.backend.admin.service.AdminRankingService;
import dev.codehouse.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminRankingController {
    private final AdminRankingService adminRankingService;

    @GetMapping("/ranking")
    public List<User> getUserRanking() {
        return adminRankingService.getUserSortedByPoint();
    }
}
