package com.noteu.noteu.member.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noteu.noteu.member.dto.MemberDetails;
import com.noteu.noteu.member.dto.SignInDto;
import com.noteu.noteu.member.security.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // 인증 시도
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.debug("JwtAuthenticationFilter : 로그인 시도.");
        ObjectMapper mapper = new ObjectMapper();
        SignInDto signInDto = mapper.readValue(request.getInputStream(), SignInDto.class);

        log.info("로그인 dto 확인: {}", signInDto);
        // Provider가 구분할 수 있도록 로그인 시도를 위한 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("authResult.getPrincipal value = {}", authResult.getPrincipal());
        MemberDetails memberDetails = (MemberDetails) authResult.getPrincipal();

        // 로그인 성공하여 jwt 발급
        String accessToken = "Bearer_" + delegateAccessToken(memberDetails);
        String refreshToken = delegateRefreshToken(memberDetails);
        response.setHeader( "Authorization", accessToken);
        response.setHeader("Refresh", refreshToken);

        log.info("header 값: {}", response.getHeader("Authorization"));
        log.info("token 값: {}", accessToken);

        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }

    private String delegateAccessToken(MemberDetails memberDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", memberDetails.getId());
        claims.put("username", memberDetails.getUsername());
        claims.put("memberName", memberDetails.getMemberName());
        claims.put("profile", memberDetails.getProfile());
        claims.put("roles", memberDetails.getAuthorities());

        String subject = memberDetails.getUsername(); // JWT 제목
        Date expiration = jwtUtils.getTokenExpiration(jwtUtils.getAccessTokenExpirationMinutes()); // 토큰 발행 일자
        String base64EncodedSecretKey = jwtUtils.encodeBase64SecretKey(jwtUtils.getSecretKey()); // Secret Key 문자열 인코딩

        return jwtUtils.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);
    }

    private String delegateRefreshToken(MemberDetails memberDetails) {
        String subject = memberDetails.getUsername();
        Date expiration = jwtUtils.getTokenExpiration(jwtUtils.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtUtils.encodeBase64SecretKey(jwtUtils.getSecretKey());

        return jwtUtils.generateRefreshToken(subject, expiration, base64EncodedSecretKey);
    }
}
