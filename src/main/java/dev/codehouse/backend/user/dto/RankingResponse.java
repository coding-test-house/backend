package dev.codehouse.backend.user.dto;


import dev.codehouse.backend.user.domain.User;
import lombok.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    private String username;
    private int point;
    private int rank;
    private String classes;

    public static List<RankingResponse> from(List<User> users) {
        AtomicInteger rank = new AtomicInteger(1);

        return users.stream()
                .map(user -> RankingResponse.builder()
                        .username(user.getUsername())
                        .point(user.getPoint())
                        .rank(rank.getAndIncrement())
                        .classes(user.getClasses())
                        .build())
                .toList();
    }
}
