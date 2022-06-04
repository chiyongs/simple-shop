package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.web.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiResponseController {

    @GetMapping("/test")
    public ApiResponse<String> hello() {
        return ApiResponse.ok("hello");
    }
}
