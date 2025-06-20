package dev.codehouse.backend.global.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public enum ResponseCode {

    //400 Validation
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다."),

    //401 Unauthorized
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 아이디입니다."),

    //200 OK
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다.");


    private final HttpStatus status;
    private final String message;

    public int getHttpStatusCode() { return status.value(); }
}
