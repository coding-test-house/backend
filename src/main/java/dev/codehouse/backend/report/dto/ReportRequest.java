package dev.codehouse.backend.report.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReportRequest {
    @NotNull(message = "신고 유형은 필수입니다.")
    private ReportType reportType;  // CHEATING, SPAM, BUG, ETC

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 200, message = "내용은 200자 이하로 작성해주세요.")
    private String content;
}
