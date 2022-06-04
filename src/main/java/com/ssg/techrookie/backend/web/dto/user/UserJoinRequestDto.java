package com.ssg.techrookie.backend.web.dto.user;

import com.ssg.techrookie.backend.domain.user.User;
import com.ssg.techrookie.backend.domain.user.UserType;
import com.ssg.techrookie.backend.web.validation.Enum;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinRequestDto {

    @NotBlank(message = "사용자 이름은 필수입니다.")
    private String userName;
    @Enum(enumClass = UserType.class, ignoreCase = true)
    private String userType;

    public User toEntity() {
        return User.builder()
                .userName(userName)
                .userType(UserType.valueOf(userType))
                .build();
    }
}
