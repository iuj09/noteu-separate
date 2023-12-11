package com.noteu.noteu.chat.entity;

import com.noteu.noteu.audit.AuditingFields;
import com.noteu.noteu.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ChatParticipant extends AuditingFields {

    @Setter
    @ManyToOne(optional = false)
    private ChatRoom chatRoom;

    @ManyToOne
    private Member member;

//    @Column
//    LocalDateTime lastStay;

//    public void updateLastStay() {
//        this.lastStay = LocalDateTime.now();
//    }
}
