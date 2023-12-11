package com.noteu.noteu.member.oauth2;

public interface OAuth2MemberInfo {

    String getProviderId();

    String getProviderType();

    String getName();

    String getEmail();

    String getTel();

    String getProfile();
}