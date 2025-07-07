package dev.codehouse.backend.user.controller;


import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.dto.UserRequest;
import dev.codehouse.backend.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserRequest request) {
        authService.register(request);
        return ApiResponseFactory.success(ResponseCode.USER_REGISTER_SUCCESS);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirm(@RequestBody UserRequest request) {
        authService.userExists(request.getUsername(), request.getClasses());
        return ApiResponseFactory.success(ResponseCode.USER_CONFIRM_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserRequest request) {
        Map<String, String> tokens = authService.login(request);
        return ApiResponseFactory.success(ResponseCode.USER_LOGIN_SUCCESS,tokens);
    }
}
