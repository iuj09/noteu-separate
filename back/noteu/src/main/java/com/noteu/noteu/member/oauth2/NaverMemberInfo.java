package com.noteu.noteu.member.oauth2;

import java.util.Map;

public class NaverMemberInfo implements OAuth2MemberInfo {
    private final Map<String, Object> attributes;

    public NaverMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getProviderType() {
        return "Naver";
    }

    @Override
    public String getName() {
        return String.valueOf(attributes.get("name"));
    }

    @Override
    public String getEmail() {
        return String.valueOf(attributes.get("email"));
    }

    @Override
    public String getTel() {
        return String.valueOf(attributes.get("mobile"));
    }

    @Override
    public String getProfile() {
        return String.valueOf(attributes.get("profile_image"));
    }
}
