package com.noteu.noteu.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberPasswordDto {
    Long id;
    String previousPassword;
    String newPassword;
}
