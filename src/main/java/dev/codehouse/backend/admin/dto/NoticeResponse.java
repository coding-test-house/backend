package dev.codehouse.backend.admin.dto;

import dev.codehouse.backend.admin.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoticeResponse {
    String title;
    String content;
    String gameInfo;
    String updatedAt;

    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getTitle(),
                notice.getContent(),
                notice.getGameInfo(),
                notice.getUpdatedAt().toString()
        );
    }
}
