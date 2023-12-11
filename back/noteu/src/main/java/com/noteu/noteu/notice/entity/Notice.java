package com.noteu.noteu.notice.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
import com.noteu.noteu.subject.entity.Subject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Notice extends AuditingFields {

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false, length = 50)
    private String noticeTitle;

    @Column(nullable = false, length = 512)
    private String noticeContent;

    public void updateNotice(String title, String content){
        this.noticeTitle = title;
        this.noticeContent = content;
    }
}
