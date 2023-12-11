package com.noteu.noteu.member.oauth2;

import java.util.Map;

public class KakaoMemberInfo implements OAuth2MemberInfo {

    private final String id;
    private final Map<String, Object> kakaoAccount;

    public KakaoMemberInfo(String id, Map<String, Object> attributes) {
        this.id = id;
        this.kakaoAccount = attributes;
    }

    @Override
    public String getProviderId() {
        return id;
    }

    @Override
    public String getProviderType() {
        return "Kakao";
    }

    @Override
    public String getName() {
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return String.valueOf(kakaoProfile.get("nickname"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(kakaoAccount.get("email"));
    }

    @Override
    public String getTel() {
        return "";
    }

    @Override
    public String getProfile() {
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return String.valueOf(kakaoProfile.get("profile_image_url"));
    }
}