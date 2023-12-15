package com.noteu.noteu.reference.dto.response;

import com.noteu.noteu.reference.entity.ReferenceRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ResponseReferenceDTO {

    private Long id;

    private Long referenceRoomId;

    private String referenceName;

    private String referenceType;

    private Long referenceSize;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
