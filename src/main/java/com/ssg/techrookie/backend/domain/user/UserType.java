package com.ssg.techrookie.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    일반("일반"),
    기업회원("기업회원");

    private String type;
}
