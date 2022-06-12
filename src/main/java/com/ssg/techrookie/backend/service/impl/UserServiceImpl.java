package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserRepository;
import com.ssg.techrookie.backend.exception.CustomException;
import com.ssg.techrookie.backend.exception.ErrorCode;
import com.ssg.techrookie.backend.service.UserService;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import com.ssg.techrookie.backend.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long join(UserJoinRequestDto requestDto) {
        log.info("[join] UserName : {}, UserType : {}", requestDto.getUserName(), requestDto.getUserType());
        return userRepository.save(requestDto.toEntity()).getId();
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        log.info("[delete] 해당 User 삭제. userId : {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId));

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto findById(Long userId) {
        log.info("[findById] 해당 User 조회. userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId));

        return new UserResponseDto(user);
    }
}
