package dev.codehouse.backend.user.controller;

import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.domain.UserHistory;
import dev.codehouse.backend.user.dto.RankingResponse;
import dev.codehouse.backend.user.dto.UserResponse;
import dev.codehouse.backend.user.repository.UserRepository;
import dev.codehouse.backend.user.service.UserFindService;
import dev.codehouse.backend.user.service.UserHistoryService;
import dev.codehouse.backend.user.service.UserRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserFindService userFindService;
    private final UserRankingService userRankingService;
    private final UserHistoryService userHistoryService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(Authentication authentication) {
        return ApiResponseFactory.success(ResponseCode.USER_FOUND, userFindService.getUser(authentication.getName()));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ApiResponseFactory.success(ResponseCode.USER_FOUND, userFindService.getAllUsers());
    }

    @GetMapping("/ranking")
    public ResponseEntity<ApiResponse<List<RankingResponse>>> getTopRanking(){
        return ApiResponseFactory.success(ResponseCode.RANK_FOUND,userRankingService.getTopRanking());
    }

    @GetMapping("/ranking/{className}")
    public ResponseEntity<ApiResponse<List<RankingResponse>>> getClassRanking(@PathVariable String className){
        return ApiResponseFactory.success(ResponseCode.RANK_FOUND,userRankingService.getClassRanking(className));
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<UserHistory>>> getUserHistory(Authentication authentication) {
        return ApiResponseFactory.success(ResponseCode.HISTORY_FOUND, userHistoryService.getUserHistory(authentication.getName()));
    }
}
