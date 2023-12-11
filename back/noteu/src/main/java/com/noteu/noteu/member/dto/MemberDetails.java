package com.noteu.noteu.member.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetails implements UserDetails, OAuth2User {
    // Member 엔티티에 UserDetails를 구현할 수도 있지만 SRP에 따라 분리함
    private Long id;
    private String username;
    private String password;
    private String memberName;
    private String profile;
    Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    // 일반 로그인 생성자
    // TODO: 오류가 나지 않으면 지울 코드
    public MemberDetails(Long id, String username, String password, String memberName, String profile, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.memberName = memberName;
        this.profile = profile;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return memberName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
