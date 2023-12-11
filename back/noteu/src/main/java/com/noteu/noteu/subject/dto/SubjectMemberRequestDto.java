package com.noteu.noteu.subject.dto;

import com.noteu.noteu.member.entity.Role;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.noteu.noteu.subject.entity.SubjectMember}
 */
@Value
public class SubjectMemberRequestDto implements Serializable {
    Long memberId;
    Long subjectId;
    String subjectCode;
}