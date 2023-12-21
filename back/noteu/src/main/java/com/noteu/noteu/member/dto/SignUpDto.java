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

    @Length(min = 4, max = 10)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String confirmPassword;

    @NotBlank
    private String memberName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String tel;

    @NotBlank
    private String role;

}
