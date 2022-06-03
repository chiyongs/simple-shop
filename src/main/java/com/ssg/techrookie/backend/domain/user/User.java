package com.ssg.techrookie.backend.domain.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String userName;
    private UserType userType;
    private UserStat userStat;

    @Builder
    public User(String userName, UserType userType) {
        this.userName = userName;
        this.userType = userType;
        this.userStat = UserStat.정상;
    }
}
