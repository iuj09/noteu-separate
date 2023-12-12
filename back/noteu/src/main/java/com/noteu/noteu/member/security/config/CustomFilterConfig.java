package com.noteu.noteu.member.security.config;

import com.noteu.noteu.member.security.filter.JwtAuthenticationFilter;
import com.noteu.noteu.member.security.filter.JwtVerificationFilter;
import com.noteu.noteu.member.security.handler.MemberAuthenticationFailureHandler;
import com.noteu.noteu.member.security.handler.MemberAuthenticationSuccessHandler;
import com.noteu.noteu.member.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomFilterConfig extends AbstractHttpConfigurer<CustomFilterConfig, HttpSecurity> {

    private final JwtUtils jwtUtils;

    public CustomFilterConfig(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {

        AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtils);
        JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtUtils);

        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new MemberAuthenticationSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new MemberAuthenticationFailureHandler());

        builder.addFilter(jwtAuthenticationFilter)
                .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
    }
}
