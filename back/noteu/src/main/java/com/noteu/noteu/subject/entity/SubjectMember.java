package com.noteu.noteu.subject.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class SubjectMember extends AuditingFields {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Subject subject;
}
