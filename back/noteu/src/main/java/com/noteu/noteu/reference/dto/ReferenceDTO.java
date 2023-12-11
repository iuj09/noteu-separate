package com.noteu.noteu.reference.dto;

import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.reference.entity.ReferenceRoom;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReferenceDTO {

    private Long id;

    private ReferenceRoom referenceRoom;

    private String referenceName;

    private String referenceType;

    private Long referenceSize;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
