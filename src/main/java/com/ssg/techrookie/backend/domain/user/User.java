package com.ssg.techrookie.backend.domain.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class User {

    @Id @GeneratedValue
    private Long id;

    private String userName;
    private String userType;
    private String userStat;

    @Builder
    public User(String userName, String userType) {
        this.userName = userName;
        this.userType = userType;
        this.userStat = "정상";
    }
}
