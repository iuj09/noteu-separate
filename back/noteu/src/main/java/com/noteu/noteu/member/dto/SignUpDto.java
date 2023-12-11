package com.noteu.noteu.member.dto;

import com.noteu.noteu.member.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {

    // TODO: 정규식 검사
    @Length(min = 4, max = 10, message = "4자 이상, 10자 이하로 입력해주세요.")
    @NotBlank
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password1;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String password2;

    @NotBlank(message = "이름을 입력해주세요.")
    private String memberName;

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String tel;

    @NotBlank(message = "타입을 선택해주세요.")
    private String role;

}
