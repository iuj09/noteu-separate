package com.noteu.noteu.member.converter;

import com.noteu.noteu.member.dto.MemberDto;
import com.noteu.noteu.member.entity.Member;

public interface MemberConverter {

    default MemberDto MemberEntityToMemberDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .profile(member.getProfile())
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .tel(member.getTel())
                .role(member.getRole())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .build();
    }
}
