package com.noteu.noteu.reference.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.entity.Subject;
import jakarta.persistence.*;
import lombok.*;


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

    @Column(columnDefinition = "bigint default 0", nullable = false)
    private Long views;

    @PrePersist
    public void prePersist() {
        this.views = this.views == null ? 0 : this.views;
    }

    public void update(String newTitle, String newContent) {
        this.referenceRoomTitle = newTitle;
        this.referenceRoomContent = newContent;
    }

}
