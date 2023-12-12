package com.noteu.noteu.config;

import com.noteu.noteu.member.security.config.CustomFilterConfig;
import com.noteu.noteu.member.security.handler.MemberAccessDeniedHandler;
import com.noteu.noteu.member.security.handler.MemberAuthenticationEntryPoint;
import com.noteu.noteu.member.security.handler.MemberAuthenticationFailureHandler;
import com.noteu.noteu.member.security.utils.JwtUtils;
import com.noteu.noteu.member.service.MemberDetailsService;
import com.noteu.noteu.member.service.OauthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final MemberDetailsService memberDetailsService;
    private final OauthDetailsService oauthDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> {
                    e.authenticationEntryPoint(new MemberAuthenticationEntryPoint());
                    e.accessDeniedHandler(new MemberAccessDeniedHandler());
                })
                .apply(new CustomFilterConfig(jwtUtils));
        http
                .userDetailsService(memberDetailsService);

        // 소셜 로그인 설정
//                .oauth2Login(oauth2Login -> oauth2Login
//                        .loginPage("/auth/login")
//                        .successHandler(new MemberAuthenticationSuccessHandler(jwtUtils))
//                        .failureHandler(new MemberAuthenticationFailureHandler())
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
//                                .userService(oauthDetailsService))
//                )
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true); // 쿠키 가능
        configuration.setAllowedOrigins(Arrays.asList(
                        "http://localhost:8081"
                )
        ); // * 은 문제 발생
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "UPDATE"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Refresh");
        configuration.setMaxAge(3000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // UrlBasedCorsConfigurationSource 생성
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 앞에서 구성한 CORS 정책 적용

        return source;
    }
}
