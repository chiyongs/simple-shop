package com.ssg.techrookie.backend.service;

import com.ssg.techrookie.backend.domain.user.UserRepository;
import com.ssg.techrookie.backend.web.dto.user.UserJoinRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(UserJoinRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }
}
