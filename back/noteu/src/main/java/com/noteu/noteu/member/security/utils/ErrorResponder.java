package com.noteu.noteu.member.security.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

public class ErrorResponder {
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String errorResponse = String.format("""
                statusValue = %s
                statusGetReasonPhrase = %s
                """, status.value(), status.getReasonPhrase());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        response.getWriter().write(mapper.writeValueAsString(errorResponse));

    }}
