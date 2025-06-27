package dev.codehouse.backend.report.service;

import dev.codehouse.backend.report.dto.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final JavaMailSender mailSender;
    private final String ADMIN_EMAIL = "jungwy980@gmail.com";

    public void submitReport(ReportType type, String email, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(ADMIN_EMAIL);
        message.setSubject("[신고 접수]" + type);
        message.setText(
                "신고 유형: " + type + "\n" +
                        "회신 이메일: " + email + "\n" +
                        "신고 내용:\n" + content
        );

        mailSender.send(message);
    }
}
