package dev.codehouse.backend.admin.service;

import dev.codehouse.backend.admin.dto.NoticeRequest;
import dev.codehouse.backend.admin.dto.NoticeResponse;
import dev.codehouse.backend.admin.entity.Notice;
import dev.codehouse.backend.admin.repository.NoticeRepository;
import dev.codehouse.backend.global.exception.AdminException;
import dev.codehouse.backend.global.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {

    private final NoticeRepository noticeRepository;

    private static final String NOTICE_ID = "main-notice";

    //공지사항 수정
    public void upsertNotice(NoticeRequest dto) {
        Notice notice = noticeRepository.findById(NOTICE_ID)
                        .orElseGet(() -> Notice.createInitialNotice(NOTICE_ID));
        notice.update(dto.getTitle(), dto.getContent(), dto.getGameInfo());
        noticeRepository.save(notice);
    }

    //공지사항 조회
    public NoticeResponse getNotice() {
        return NoticeResponse.from(
                noticeRepository.findById(NOTICE_ID)
                        .orElseThrow(() -> new AdminException(ResponseCode.NOTICE_NOT_FOUND))
        );
    }
}
