package dev.codehouse.backend.user.dto;

import dev.codehouse.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String classes;
    private int point;

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getClasses(),
                user.getPoint()
        );
    }
}
