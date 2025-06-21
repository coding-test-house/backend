package dev.codehouse.backend.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "problems")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    private String id;
    private String title;
    private String problemNumber;
    private String url;
    private String day;
    private LocalDateTime createdAt;

    public static Problem createProblem(String title, String problemNumber, String url, String day) {
        return Problem.builder()
                .title(title)
                .problemNumber(problemNumber)
                .url(url)
                .day(day)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
