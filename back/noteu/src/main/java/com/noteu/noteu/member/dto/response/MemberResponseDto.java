package com.noteu.noteu.member.dto.response;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String username;
    private String membername;
    private String introduction;
    private String profile;
    private String email;
    private String tel;
}
