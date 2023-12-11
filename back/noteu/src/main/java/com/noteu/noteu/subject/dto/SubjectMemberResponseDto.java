package com.noteu.noteu.subject.dto;

import com.noteu.noteu.member.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.noteu.noteu.subject.entity.SubjectMember}
 */
@Value
@Builder
public class SubjectMemberResponseDto implements Serializable {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    Long memberId;
    String memberUsername;
    String memberProfile;
    String memberMemberName;
    String memberEmail;
    String memberPhone;
    List<Role> memberRole;
    Long subjectId;
    String subjectSubjectName;
}