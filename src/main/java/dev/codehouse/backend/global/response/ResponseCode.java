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
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST,"비밀번호가 일치하지 않습니다"),
    //401 Unauthorized
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "존재하지 않는 아이디입니다."),

    //404
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항이 존재하지 않습니다."),
    PROBLEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 문제가 존재하지 않습니다."),
    CLASS_NOT_FOUND(HttpStatus.NOT_FOUND, "회차 정보가 올바르지 않습니다"),

    //500 Internal Server Error
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다"),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 호출 중 오류가 발생했습니다."),
    PROBLEM_ALREADY_EXISTS(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 문제입니다"),

    //200 OK
    USER_LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공했습니다."),
    USER_REGISTER_SUCCESS(HttpStatus.CREATED, "회원가입에 성공했습니다"),
    USER_CONFIRM_SUCCESS(HttpStatus.OK,"사용자 확인에 성공했습니다"),
    REPORT_SUBMIT_SUCCESS(HttpStatus.OK, "신고 제출에 성공했습니다."),
    NOTICE_FOUND(HttpStatus.OK, "공지사항 조회 성공"),
    NOTICE_UPDATED(HttpStatus.OK, "공지사항 수정 완료"),
    USER_POINT_UPDATED(HttpStatus.OK, "사용자 포인트가 수정되었습니다."),
    PROBLEM_FOUND(HttpStatus.OK, "문제 조회 성공"),
    PROBLEM_DELETED(HttpStatus.OK, "문제 삭제 성공"),
    USER_FOUND(HttpStatus.OK, "유저 조회 성공"),
    RANK_FOUND(HttpStatus.OK, "랭킹 조회 성공"),
    HISTORY_FOUND(HttpStatus.OK, "정산내역 조회 성공"),

    //201
    NOTICE_CREATED(HttpStatus.CREATED, "초기 공지사항이 생성되었습니다."),
    PROBLEM_REGISTERED(HttpStatus.CREATED, "문제 등록 성공");

    private final HttpStatus status;
    private final String message;

    public int getHttpStatusCode() { return status.value(); }
}
