package com.ll.medium.domain.member.member.role;

import lombok.Getter;

@Getter
public enum MemberRole {    //enum은 열거 자료형
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}
