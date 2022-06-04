package com.ssg.techrookie.backend.service.impl;

import com.ssg.techrookie.backend.domain.user.UserRepository;
import com.ssg.techrookie.backend.service.UserService;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long join(UserJoinRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }
}
