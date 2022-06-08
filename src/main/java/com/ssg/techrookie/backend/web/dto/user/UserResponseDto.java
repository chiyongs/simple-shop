package com.ssg.techrookie.backend.web.dto.user;

import com.ssg.techrookie.backend.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String userName;
    private String userType;
    private String userStat;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.userName = entity.getUserName();
        this.userType = entity.getUserType().getType();
        this.userStat = entity.getUserStat().getStatus();
    }
}
