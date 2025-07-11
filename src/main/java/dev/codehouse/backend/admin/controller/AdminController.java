package dev.codehouse.backend.admin.controller;

import dev.codehouse.backend.admin.dto.*;
import dev.codehouse.backend.admin.service.AdminNoticeService;
import dev.codehouse.backend.admin.service.AdminPointService;
import dev.codehouse.backend.admin.service.AdminProblemService;
import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.user.dto.UserResponse;
import dev.codehouse.backend.user.service.UserFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminNoticeService noticeService;
    private final AdminPointService pointService;
    private final AdminProblemService problemService;
    private final UserFindService userFindService;

    @GetMapping("/notice")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNotice() {
        return ApiResponseFactory.success(ResponseCode.NOTICE_FOUND, noticeService.getNotice());
    }

    @PostMapping("/notice")
    public ResponseEntity<ApiResponse<Void>> updateNotice(@RequestBody NoticeRequest dto) {
        noticeService.upsertNotice(dto);
        return ApiResponseFactory.success(ResponseCode.NOTICE_UPDATED);
    }

    @PatchMapping("/points")
    public ResponseEntity<ApiResponse<UserPointResponse>> adjustPoint(@RequestBody UserPointRequest dto) {
        return ApiResponseFactory.success(ResponseCode.USER_POINT_UPDATED, pointService.adjustUserPoint(dto.getUsername(), dto.getDelta()));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ApiResponseFactory.success(ResponseCode.USER_FOUND, userFindService.getAllUsers());
    }

    @PostMapping("/problem")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody AdminProblemRequest request) {
        problemService.saveProblem(request);
        return ApiResponseFactory.success(ResponseCode.PROBLEM_REGISTERED);
    }

    @DeleteMapping("/problem/{number}")
    public ResponseEntity<ApiResponse<Void>> deleteProblem(@PathVariable String number) {
        problemService.deleteProblem(number);
        return ApiResponseFactory.success(ResponseCode.PROBLEM_DELETED);
    }

    @GetMapping("/problem")
    public ResponseEntity<ApiResponse<List<AdminProblemResponse>>> getAllProblemsPaged(@RequestParam(defaultValue = "0") int page) {
//        return ApiResponseFactory.success(ResponseCode.PROBLEM_FOUND, problemService.getAllProblemsPaged(page));
        return ApiResponseFactory.success(ResponseCode.PROBLEM_FOUND, problemService.getAllProblems());
    }

}
