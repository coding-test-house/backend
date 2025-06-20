package dev.codehouse.backend.admin.controller;

import dev.codehouse.backend.admin.dto.NoticeRequest;
import dev.codehouse.backend.admin.dto.NoticeResponse;
import dev.codehouse.backend.admin.service.AdminNoticeService;
import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminNoticeService noticeService;

    @GetMapping("/notice")
    public ResponseEntity<ApiResponse<NoticeResponse>> getNotice() {
        return ApiResponseFactory.success(ResponseCode.NOTICE_FOUND, noticeService.getNotice());
    }

    @PostMapping("/notice")
    public ResponseEntity<ApiResponse<Void>> createNotice() {
        noticeService.createInitialNotice();
        return ApiResponseFactory.success(ResponseCode.NOTICE_CREATED);
    }

    @PutMapping("/notice")
    public ResponseEntity<ApiResponse<Void>> updateNotice(@RequestBody NoticeRequest dto) {
        noticeService.updateNotice(dto);
        return ApiResponseFactory.success(ResponseCode.NOTICE_UPDATED);
    }
}
