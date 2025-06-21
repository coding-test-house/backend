package dev.codehouse.backend.global.exception;

import dev.codehouse.backend.global.response.ResponseCode;

public class AdminException extends BaseException {
    public AdminException(ResponseCode code) {
        super(code);
    }
}
