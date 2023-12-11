package com.noteu.noteu.task.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
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
public class TaskComment extends AuditingFields {

    @ManyToOne(fetch = FetchType.EAGER)
    private Task task;

    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

    @Column(nullable = false, length = 128)
    private String taskCommentTitle;

    @Column(nullable = false, length = 50)
    private String taskCommentFileName;

    public void updateTaskComment(String title, String fileName){
        this.taskCommentTitle = title;
        this.taskCommentFileName = fileName;
    }
}
