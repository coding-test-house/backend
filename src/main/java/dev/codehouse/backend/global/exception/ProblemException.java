package dev.codehouse.backend.global.exception;

import dev.codehouse.backend.global.response.ResponseCode;

public class ProblemException extends BaseException {
    public ProblemException(ResponseCode code) {
        super(code);
    }
}
