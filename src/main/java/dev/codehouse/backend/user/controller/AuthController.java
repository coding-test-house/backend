package dev.codehouse.backend.user.controller;


import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.domain.User;
import dev.codehouse.backend.user.dto.UserRequestDto;
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
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserRequestDto request) {
        try {
            authService.register(request);
        } catch (IllegalArgumentException e){
            if(e.getMessage().contains("회차")){
                ApiResponseFactory.success(ResponseCode.CLASS_NOT_FOUND);
            }
            if(e.getMessage().contains("error")){
                return ApiResponseFactory.success(ResponseCode.USER_NOT_FOUND);
            }
            return ApiResponseFactory.success(ResponseCode.CLASS_NOT_FOUND);
        } catch (Exception e) {
            return ApiResponseFactory.success(ResponseCode.DUPLICATE_USERNAME);
        }
        return ApiResponseFactory.success(ResponseCode.USER_REGISTER_SUCCESS);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<Void>> confirm(@RequestBody UserRequestDto request) {
        try {
            System.out.println(request.getUsername()+ " " + request.getClasses());
            authService.userExists(request.getUsername(), request.getClasses());
        } catch (IllegalArgumentException e) {
            if(e.getMessage().contains("회차"))
                return ApiResponseFactory.success(ResponseCode.CLASS_NOT_FOUND);
            return ApiResponseFactory.success(ResponseCode.USER_NOT_FOUND);
        }
        catch (Exception e) {
            return ApiResponseFactory.success(ResponseCode.DATABASE_ERROR);
        }
        return ApiResponseFactory.success(ResponseCode.USER_CONFIRM_SUCCESS);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody UserRequestDto request) {
        Map<String, String> tokens = null;
        try {
             tokens = authService.login(request);
        } catch (Exception e) {
            if(e.getMessage().contains("존재")){
                return ApiResponseFactory.success(ResponseCode.USER_NOT_FOUND,tokens);
            } else if(e.getMessage().contains("회차")){
                return ApiResponseFactory.success(ResponseCode.CLASS_NOT_FOUND,tokens);
            }
            System.out.println(e.getMessage());
            return ApiResponseFactory.success(ResponseCode.INVALID_PASSWORD,tokens);
        }
        return ApiResponseFactory.success(ResponseCode.USER_LOGIN_SUCCESS, tokens);
    }
}
