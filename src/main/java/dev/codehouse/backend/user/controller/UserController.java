package dev.codehouse.backend.user.controller;

import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.dto.RankingResponseDto;
import dev.codehouse.backend.user.dto.UserResponseDto;
import dev.codehouse.backend.user.repository.UserRepository;
import dev.codehouse.backend.user.service.UserFindService;
import dev.codehouse.backend.user.service.UserRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    private final UserRepository userRepository;
    private final UserRankingService userRankingService;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUser(@PathVariable String username) {
        return ApiResponseFactory.success(ResponseCode.USER_FOUND, userFindService.getUser(username));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        return ApiResponseFactory.success(ResponseCode.USER_FOUND, userFindService.getAllUsers());
    }


    @GetMapping("/{username}/point")
    public Map<String, Integer> getPoint(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
        return Map.of("point", user.getPoint());
    }

    @GetMapping("/toprank/all")
    public ResponseEntity<ApiResponse<List<RankingResponseDto>>> getTopRanking(){
        return ApiResponseFactory.success(ResponseCode.RANK_FOUND,userRankingService.getTopRanking());
    }

    @GetMapping("/toprank/class/{className}")
    public ResponseEntity<ApiResponse<List<RankingResponseDto>>> getClassRanking(@PathVariable String className){
        return ApiResponseFactory.success(ResponseCode.RANK_FOUND,userRankingService.getclassRanking(className));
    }
}
