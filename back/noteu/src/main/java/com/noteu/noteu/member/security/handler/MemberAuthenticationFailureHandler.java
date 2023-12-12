package com.noteu.noteu.member.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.ErrorResponse;

import java.io.IOException;

@Slf4j
public class MemberAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error("login authentication failed!!! {}", e.getMessage());
        sendErrorResponse(response, e);
    }

    private void sendErrorResponse(HttpServletResponse response, AuthenticationException e) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse errorResponse = ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, "입력 정보가 올바르지 않습니다.");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }
}
