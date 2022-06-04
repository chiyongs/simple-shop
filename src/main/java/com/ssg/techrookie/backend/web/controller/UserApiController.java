package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.UserService;
import com.ssg.techrookie.backend.service.impl.UserServiceImpl;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<Long> userAdd(@Validated @RequestBody UserJoinRequestDto requestDto) {
        return ApiResponse.ok(userService.join(requestDto));
    }
}
