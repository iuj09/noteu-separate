package com.noteu.noteu.member.handler;

import com.noteu.noteu.member.dto.MemberDetails;
import com.noteu.noteu.member.dto.MemberInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();

        // 권한 정보
        Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) ((List) memberDetails.getAuthorities()).stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toUnmodifiableSet());

        log.info("authorities : {} ", authorities);

        Authentication memberInfo = new UsernamePasswordAuthenticationToken(
                MemberInfo.builder()
                        .id(memberDetails.getId())
                        .username(memberDetails.getUsername())
                        .memberName(memberDetails.getName())
                        .profile(memberDetails.getProfile())
                        .authorities(authorities)
                        .build()
                , null, authorities);

        // SecurityContextHolder에 MemberInfo 저장
        SecurityContextHolder.getContext().setAuthentication(memberInfo);
        log.info("SecurityContextHolder 저장 객체: {}", SecurityContextHolder.getContext().getAuthentication());

        response.sendRedirect(request.getContextPath() + "/subjects");
    }
}
