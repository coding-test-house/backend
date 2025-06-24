package dev.codehouse.backend.admin.dto;

import dev.codehouse.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPointResponse {
    String userId;
    String userName;
    int point;

    public static UserPointResponse from(User user) {
        return new UserPointResponse(
                user.getId(),
                user.getUsername(),
                user.getPoint()
        );
    }
}
