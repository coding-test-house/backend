package dev.codehouse.backend.global.exception;

import dev.codehouse.backend.global.response.ResponseCode;

public class UserException extends BaseException {
    public UserException(ResponseCode code) {
        super(code);
    }
}
