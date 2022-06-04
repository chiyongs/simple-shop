package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class) // Spring Validation @Validated (잘못된 Request 파라미터 경우)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ErrorResponse.error(ErrorCode.NOT_VALID_METHOD_ARGUMENT);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class) // 잘못된 httpMethod로 접근한 경우
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ErrorResponse.error(ErrorCode.NOT_SUPPORTED_HTTP_METHOD);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.error(e.getErrorCode());
    }


}
