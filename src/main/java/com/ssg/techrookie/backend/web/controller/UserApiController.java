package com.ssg.techrookie.backend.web.controller;

import com.ssg.techrookie.backend.service.UserService;
import com.ssg.techrookie.backend.service.impl.UserServiceImpl;
import com.ssg.techrookie.backend.web.dto.ApiResponse;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import com.ssg.techrookie.backend.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<Long> userAdd(@Validated @RequestBody UserJoinRequestDto requestDto) {
        return ApiResponse.ok(userService.join(requestDto));
    }

    @DeleteMapping
    public ApiResponse<Long> userDelete(@Validated @RequestParam Long userId) {
        userService.delete(userId);
        return ApiResponse.ok(userId);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> userDetail(@Validated @PathVariable Long userId) {
        return ApiResponse.ok(userService.findById(userId));
    }
}
