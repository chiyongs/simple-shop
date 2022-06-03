package com.ssg.techrookie.backend.web.dto.user;

import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequestDto {

    private String userName;
    private String userType;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .userType(UserType.valueOf(userType))
                .build();
    }
}
