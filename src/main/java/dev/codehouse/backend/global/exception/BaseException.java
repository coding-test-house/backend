package dev.codehouse.backend.global.exception;

import dev.codehouse.backend.global.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private final ResponseCode code;

    @Override
    public String getMessage() {
        return code.getMessage();
    }
}
