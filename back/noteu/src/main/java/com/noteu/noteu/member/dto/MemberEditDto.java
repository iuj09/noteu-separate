package com.noteu.noteu.member.dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEditDto {

    private Long id;

    private String memberName;

    private String email;

    private String tel;

    private String introduction;
}
