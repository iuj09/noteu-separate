package com.noteu.noteu.reference.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Reference extends AuditingFields {

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ReferenceRoom referenceRoom;

    @Column(nullable = false, length = 100)
    private String referenceName;

    @Column(nullable = false, length = 100)
    private String referenceType;

    @Column(nullable = false)
    private Long referenceSize;
}
