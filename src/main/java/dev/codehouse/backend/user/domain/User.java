
package dev.codehouse.backend.user.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.*;

@Document(collection = "users")  // MongoDB 컬렉션 이름 지정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;  // MongoDB의 ObjectId는 String으로 처리

    @Indexed(unique = true)  // 유니크 인덱스 생성
    private String username;

    private String password;

    private String classes;

    // 추후 역할 확장 고려
    @Builder.Default
    private String role = "USER";

    // 예: 누적 포인트
    @Builder.Default
    private int startPoint = 0;

    @Builder.Default
    private int point = 0;
}