package dev.codehouse.backend.user.dto;

import dev.codehouse.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String classes;
    private int point;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getClasses(),
                user.getPoint()
        );
    }
}
