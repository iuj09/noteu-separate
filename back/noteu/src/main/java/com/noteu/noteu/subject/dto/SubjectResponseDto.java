package com.noteu.noteu.subject.dto;

import lombok.Builder;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@Builder
public class SubjectResponseDto implements Serializable {
    Long id;
    LocalDateTime createdAt;
    LocalDateTime modifiedAt;
    String subjectName;
    String subjectCode;
}