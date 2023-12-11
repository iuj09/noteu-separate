package com.noteu.noteu.member.oauth2;

import java.util.Map;

public class GoogleMemberInfo implements OAuth2MemberInfo {
    private final Map<String, Object> attributes;

    public GoogleMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return String.valueOf(attributes.get("sub"));
    }

    @Override
    public String getProviderType() {
        return "Google";
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
        return "";
    }

    @Override
    public String getProfile() {
        return String.valueOf(attributes.get("picture"));
    }
}

