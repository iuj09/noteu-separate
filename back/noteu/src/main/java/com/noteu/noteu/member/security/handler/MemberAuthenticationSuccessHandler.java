package com.noteu.noteu.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noteu.noteu.member.dto.MemberDetails;
import com.noteu.noteu.member.dto.MemberInfo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("success handler 시작");

        ObjectMapper mapper = new ObjectMapper();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        log.info("MemberAuthenticationSuccessHandler auth.getPrincipal : {}", authentication.getPrincipal().toString());

        MemberInfo memberInfo = MemberInfo.builder()
                .id(memberDetails.getId())
                .username(memberDetails.getUsername())
                .memberName(memberDetails.getMemberName())
                .profile(memberDetails.getProfile())
                .authorities(memberDetails.getAuthorities())
                .build();
        String memberInfoToJson = mapper.writeValueAsString(memberInfo);
        response.setContentType("application/json");
        response.getWriter().write(memberInfoToJson);
        log.info("login authentication success!!!");
        log.info("memberInfo: {}", memberInfo.toString());

        String authorization = response.getHeader("Authorization");
        log.info("successHandler 토큰 값: {}", authorization);
    }
}
