package com.noteu.noteu.reference.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.noteu.noteu.subject.entity.Subject;
import com.noteu.noteu.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReferenceRoomDTO {
    private Long id;

    private Subject subject;

    private Member member;

    private String referenceRoomTitle;

    private String referenceRoomContent;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
