package com.noteu.noteu.reference.dto.response;

import com.noteu.noteu.subject.entity.Subject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetAllResponseReferenceRoomDTO {

    private Long referenceRoomId;

    private Long subjectId;

    private Long memberId;

    private String memberName;

    private String referenceRoomTitle;

    private Long views;

    private LocalDateTime createdAt;
}
