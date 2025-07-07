package dev.codehouse.backend.global.exception;

import com.mongodb.DuplicateKeyException;
import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException e) {
        return ResponseEntity
                .status(e.getCode().getStatus())
                .body(ApiResponse.error(e.getCode()));
    }

    //Dto Valid 관련 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = (fieldError != null) ? fieldError.getDefaultMessage() : "잘못된 요청입니다.";

        return ResponseEntity
                .status(ResponseCode.INVALID_REQUEST.getStatus())
                .body(ApiResponse.error(ResponseCode.INVALID_REQUEST.getHttpStatusCode(), message));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateKeyException(DuplicateKeyException e) {
        return ResponseEntity.status(ResponseCode.DUPLICATE_USERNAME.getStatus())
                .body(ApiResponse.error(ResponseCode.DUPLICATE_USERNAME));
    }
}
