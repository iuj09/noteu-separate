package com.noteu.noteu.question.entity;

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
public class QuestionPost extends AuditingFields {

    @ManyToOne(fetch = FetchType.LAZY)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false, length = 128)
    private String questionPostTitle;

    @Column(nullable = false, length = 1024)
    private String questionPostContent;

    public void update(String newTitle, String newContent) {
        this.questionPostTitle = newTitle;
        this.questionPostContent = newContent;
    }
}
