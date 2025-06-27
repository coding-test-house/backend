package dev.codehouse.backend.report.controller;

import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ApiResponseFactory;
import dev.codehouse.backend.global.response.ResponseCode;
import dev.codehouse.backend.report.dto.ReportRequest;
import dev.codehouse.backend.report.service.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> submitReport(@RequestBody @Valid ReportRequest request) {
        reportService.submitReport(request.getReportType(), request.getEmail(), request.getContent());
        return ApiResponseFactory.success(ResponseCode.REPORT_SUBMIT_SUCCESS);
    }
}
