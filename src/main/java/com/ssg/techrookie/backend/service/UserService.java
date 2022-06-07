package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;

public interface UserService {
    Long join(UserJoinRequestDto requestDto);
    void delete(Long userId);

}
