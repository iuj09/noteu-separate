package com.noteu.noteu.subject.entity;

import com.noteu.noteu.audit.AuditingFields;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Subject extends AuditingFields {

    @Column(nullable = false, length = 30)
    private String subjectName;

    @Column(nullable = false, length = 10)
    private String subjectCode;
}
