package dev.codehouse.backend.global.exception;

import dev.codehouse.backend.global.response.ResponseCode;

public class AuthException extends BaseException{

    public AuthException(ResponseCode code) {
        super(code);
    }
}
