package com.ssg.techrookie.backend.web.dto;

import com.ssg.techrookie.backend.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> extends ErrorResponse {
    private final T data;

    public ApiResponse(T data) {
        super(ErrorCode.SUCCESS);
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(T response) {
        return new ApiResponse<>(response);
    }

}
