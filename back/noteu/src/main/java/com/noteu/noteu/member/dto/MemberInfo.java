package com.noteu.noteu.member.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Slf4j
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
    // 서버 내에서 인증된 사용자의 정보를 가져올 수 있는 객체
    private Long id;
    private String username;
    private String memberName;
    private String profile;
    private Collection<? extends GrantedAuthority> authorities;
}
