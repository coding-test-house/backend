package dev.codehouse.backend.user.controller;


import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.dto.UserRequestDto;
import dev.codehouse.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserRequestDto request) {
        userService.register(request);
        return ApiResponseFactory.success(ResponseCode.USER_REGISTER_SUCCESS);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirm(@RequestBody UserRequestDto request) {
        userService.userExists(request.getUsername());
        return ApiResponseFactory.success(ResponseCode.USER_CONFIRM_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserRequestDto request) {
        Map<String, String> tokens = userService.login(request);
        return ApiResponseFactory.success(ResponseCode.USER_LOGIN_SUCCESS, tokens);
    }

}
