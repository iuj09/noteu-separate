package com.noteu.noteu.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor // 스프링 시큐리티에서 권한을 문자열로 비교하는데, 이것은 순서(0)만 가지고 있기 때문에 게터와 생성자를 넣어 value값을 넣어준다.
public enum Role {
    ADMIN("ROLE_ADMIN"), TEACHER("ROLE_TEACHER"), STUDENT("ROLE_STUDENT");

    private String value;

    public static List<Role> equals(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getValue().equals(role))
                .toList();
    }

}
