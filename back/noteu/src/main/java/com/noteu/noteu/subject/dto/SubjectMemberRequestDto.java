package com.noteu.noteu.subject.dto;

import lombok.*;

/**
 * DTO for {@link com.noteu.noteu.subject.entity.SubjectMember}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectMemberRequestDto {
    String subjectCode;
}