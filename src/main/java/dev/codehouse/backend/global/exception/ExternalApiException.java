package dev.codehouse.backend.global.exception;

import dev.codehouse.backend.global.response.ResponseCode;

public class ExternalApiException extends BaseException {
    public ExternalApiException(ResponseCode code) {
        super(code);
    }
}
