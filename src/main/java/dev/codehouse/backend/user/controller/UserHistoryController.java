package dev.codehouse.backend.user.controller;

import dev.codehouse.backend.user.dto.HistoryRequest;
import dev.codehouse.backend.user.service.UserHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class UserHistoryController {
    private final UserHistoryService userHistoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addHistory(
            @RequestBody HistoryRequest request
    ) {
        userHistoryService.addUserHistory(request.getUsername(), request.getType(), request.getReason(), request.getAmount());
        return ResponseEntity.ok("history 에 추가 완료");
    }
}
