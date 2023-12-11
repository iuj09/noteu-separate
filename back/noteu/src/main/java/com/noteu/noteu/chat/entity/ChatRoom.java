package com.noteu.noteu.chat.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.subject.entity.Subject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class ChatRoom extends AuditingFields {

    @ManyToOne
    private Subject subject;

    @ToString.Exclude
    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private final Set<ChatParticipant> participants = new LinkedHashSet<>();
}
