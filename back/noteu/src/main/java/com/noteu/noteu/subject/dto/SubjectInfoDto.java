package com.noteu.noteu.subject.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SubjectInfoDto {
    Long memberId;
    Long subjectId;
    String teacherName;
    String subjectName;
    LocalDateTime joinedAt; // 과목 참여 날짜
}
