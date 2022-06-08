package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import com.ssg.techrookie.backend.web.dto.user.UserResponseDto;

public interface UserService {
    Long join(UserJoinRequestDto requestDto);
    void delete(Long userId);
    UserResponseDto findById(Long userId);
}
