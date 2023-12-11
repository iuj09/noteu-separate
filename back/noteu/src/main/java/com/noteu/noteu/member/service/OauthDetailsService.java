package com.noteu.noteu.member.service;

import com.noteu.noteu.member.dto.MemberDetails;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.member.entity.Role;
import com.noteu.noteu.member.oauth2.GoogleMemberInfo;
import com.noteu.noteu.member.oauth2.KakaoMemberInfo;
import com.noteu.noteu.member.oauth2.NaverMemberInfo;
import com.noteu.noteu.member.oauth2.OAuth2MemberInfo;
import com.noteu.noteu.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OauthDetailsService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // Resource Server로부터 받은 userRequest 데이터 후처리
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // registraionId로 어떤 OAuth로 로그인 했는지 확인
        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes: " + super.loadUser(userRequest).getAttributes());

        // OAuth 로그인 회원 가입
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2MemberInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            oAuth2UserInfo = new KakaoMemberInfo(String.valueOf(oAuth2User.getAttributes().get("id")),
                    (Map) oAuth2User.getAttributes().get("kakao_account"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleMemberInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverMemberInfo((Map) oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("지원하지 않는 로그인 서비스 입니다.");
        }

        String providerType = oAuth2UserInfo.getProviderType();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = providerType.toLowerCase() + "_" + providerId;
        if (username.length() > 20) {
            username = username.substring(0, 20);
        }

        Member member = memberRepository.findByUsername(username).orElse(null);

        if (member == null) {
            String profile = oAuth2UserInfo.getProfile();
            if (profile.equals("null")) {
                profile = "/file/profile/default.png"; // 기본 이미지
            }
            member = Member.builder()
                    .username(username)
                    .password(passwordEncoder.encode("*OauthPassword*"))
                    .profile(profile)
                    .memberName(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .tel(oAuth2UserInfo.getTel())
                    .role(Role.equals("ROLE_STUDENT"))
                    .build();
            memberRepository.save(member);
        }

        List<SimpleGrantedAuthority> list = member.getRole().stream()
                .map(Role::getValue)
                .map(SimpleGrantedAuthority::new)
                .toList();


        return MemberDetails.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .profile(member.getProfile())
                .memberName(member.getMemberName())
                .authorities(list)
                .build();
    }
}