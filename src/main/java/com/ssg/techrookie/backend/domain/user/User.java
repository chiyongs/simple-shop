package com.ssg.techrookie.backend.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Enumerated(EnumType.STRING)
    private UserStat userStat;

    @Builder
    public User(String userName, UserType userType) {
        this.userName = userName;
        this.userType = userType;
        this.userStat = UserStat.정상;
    }

    //== 비즈니스 로직 ==//
    /** 회원 탈퇴 **/
    public void withdraw() {
        this.userStat = UserStat.탈퇴;
    }
}
