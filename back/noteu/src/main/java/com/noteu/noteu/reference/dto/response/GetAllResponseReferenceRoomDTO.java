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

    private Subject subject;

    private Long memberId;

    private String memberName;

    private String referenceRoomTitle;

    private LocalDateTime createdAt;
}
