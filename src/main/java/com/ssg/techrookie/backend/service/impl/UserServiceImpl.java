package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserRepository;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.service.UserService;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long join(UserJoinRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }
}
