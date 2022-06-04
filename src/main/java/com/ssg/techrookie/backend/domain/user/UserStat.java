package com.ssg.techrookie.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStat {
    정상("정상"),
    탈퇴("탈퇴");

    private String status;
}
