package com.ssg.techrookie.backend.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ItemType {
    일반("일반"),
    기업회원상품("기업회원상품");

    private String type;
}
