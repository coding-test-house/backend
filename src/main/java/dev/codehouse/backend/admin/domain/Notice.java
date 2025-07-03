package dev.codehouse.backend.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notices")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    private String id;
    private String title;
    private String content;
    private String gameInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(String title, String content, String gameInfo) {
        this.title = title;
        this.content = content;
        this.gameInfo = gameInfo;
        this.updatedAt = LocalDateTime.now();
    }

    public static Notice createInitialNotice(String id) {
        LocalDateTime now = LocalDateTime.now();
        return Notice.builder()
                .id(id)
                .title("초기 공지")
                .content("")
                .gameInfo("")
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}

