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

    /**
     * 사용자 등록 메서드
     * @param requestDto (사용자에 대한 정보)
     * @return 등록한 사용자의 id
     */
    @PostMapping
    public ApiResponse<Long> userAdd(@Validated @RequestBody UserJoinRequestDto requestDto) {
        return ApiResponse.ok(userService.join(requestDto));
    }

    /**
     * 사용자 id에 해당하는 사용자 삭제 메서드
     * @param userId
     * @return 삭제된 사용자의 id
     */
    @DeleteMapping
    public ApiResponse<Long> userDelete(@Validated @RequestParam Long userId) {
        userService.delete(userId);
        return ApiResponse.ok(userId);
    }

    /**
     * 사용자 id에 해당하는 사용자 정보 반환 메서드
     * @param userId
     * @return 사용자 정보를 담고 있는 dto
     */
    @GetMapping("/{userId}")
    public ApiResponse<UserResponseDto> userDetail(@Validated @PathVariable Long userId) {
        return ApiResponse.ok(userService.findById(userId));
    }
}
