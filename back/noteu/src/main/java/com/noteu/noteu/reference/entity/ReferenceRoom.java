package com.noteu.noteu.reference.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.entity.Subject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReferenceRoom extends AuditingFields {

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false, length = 128)
    private String referenceRoomTitle;

    @Column(nullable = false, length = 1024)
    private String referenceRoomContent;

    public void update(String newTitle, String newContent) {
        this.referenceRoomTitle = newTitle;
        this.referenceRoomContent = newContent;
    }
}
