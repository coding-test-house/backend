package dev.codehouse.backend.global.exception;

import com.mongodb.DuplicateKeyException;
import dev.codehouse.backend.global.response.ApiResponse;
import dev.codehouse.backend.global.response.ResponseCode;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateKeyException(DuplicateKeyException e) {
        return ResponseEntity.status(ResponseCode.DUPLICATE_USERNAME.getStatus())
                .body(ApiResponse.error(ResponseCode.DUPLICATE_USERNAME));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthException(BaseException e) {
        return ResponseEntity
                .status(e.getCode().getStatus())
                .body(ApiResponse.error(e.getCode()));
    }





//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
//        ResponseCode responseCode = determineResponseCode(e.getMessage());
//        return ResponseEntity.status(responseCode.getStatus())
//                .body(ApiResponse.error(responseCode));
//    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException e) {
        ResponseCode responseCode = e.getMessage().contains("API") ?
                ResponseCode.EXTERNAL_API_ERROR : ResponseCode.DATABASE_ERROR;
        return ResponseEntity.status(responseCode.getStatus())
                .body(ApiResponse.error(responseCode));
    }

//    private ResponseCode determineResponseCode(String message) {
//        if (message.contains("사용자를 찾을 수 없") || message.contains("존재하지 않는")) {
//            return ResponseCode.USER_NOT_FOUND;
//        } else if (message.contains("비밀번호") || message.contains("일치하지 않")) {
//            return ResponseCode.INVALID_PASSWORD;
//        } else {
//            return ResponseCode.INVALID_REQUEST;
//        }
//    }
}
