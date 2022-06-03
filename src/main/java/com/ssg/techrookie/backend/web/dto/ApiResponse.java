package com.ssg.techrookie.backend.web.dto;

import com.ssg.techrookie.backend.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final int status;
    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> ok(T response) {
        return ApiResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .code(ErrorCode.SUCCESS.name())
                .message(ErrorCode.SUCCESS.getMessage())
                .data(response)
                .build();
    }
}
