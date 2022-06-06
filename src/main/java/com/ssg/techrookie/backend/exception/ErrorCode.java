package com.ssg.techrookie.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //== 200 ==//
    SUCCESS(HttpStatus.OK, "OK"),

    //== 400 ==//
    NOT_SUPPORTED_HTTP_METHOD(HttpStatus.BAD_REQUEST,"지원하지 않는 Http Method 방식입니다."),
    NOT_VALID_METHOD_ARGUMENT(HttpStatus.BAD_REQUEST,"유효하지 않은 Request Body 혹은 Argument입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자입니다."),
    ITEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 상품입니다."),
    PROMOTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 프로모션입니다."),
    NO_PERMISSION_TO_LEFT_USER(HttpStatus.BAD_REQUEST, "탈퇴한 사용자는 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

}
