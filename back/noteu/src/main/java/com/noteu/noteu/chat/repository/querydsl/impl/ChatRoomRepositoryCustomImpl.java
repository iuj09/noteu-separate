package com.noteu.noteu.chat.repository.querydsl.impl;

import com.noteu.noteu.chat.entity.*;
import com.noteu.noteu.chat.repository.querydsl.ChatRoomRepositoryCustom;
import com.noteu.noteu.member.entity.QMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

@Slf4j
public class ChatRoomRepositoryCustomImpl extends QuerydslRepositorySupport implements ChatRoomRepositoryCustom {

    public ChatRoomRepositoryCustomImpl() {
        super(ChatRoom.class);
    }

    @Override
    public List<ChatRoom> findAllBySubjectId(Long subjectId, Long loginId) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChatParticipant chatParticipant = QChatParticipant.chatParticipant;
        QMember member = QMember.member;
        QChatMessage chatMessage = QChatMessage.chatMessage;

        return from(chatRoom)
                .join(chatRoom.participants, chatParticipant)
                .join(chatParticipant.member, member)
                .leftJoin(chatMessage).on(chatRoom.id.eq(chatMessage.roomId))
                .where(chatRoom.subject.id.eq(subjectId)
                        .and(member.id.eq(loginId)))
                .orderBy(chatMessage.createdAt.desc())
                .select(chatRoom)
                .fetch();
    }

    @Override
    public Optional<ChatRoom> existsChatRoom(Long subjectId, Long friendId, Long loginId) {
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QChatParticipant chatParticipant = QChatParticipant.chatParticipant;

        return Optional.ofNullable(
                from(chatRoom)
                        .join(chatRoom.participants, chatParticipant)
                        .where(chatRoom.subject.id.eq(subjectId)
                                .and(chatParticipant.member.id.in(friendId, loginId)))
                        .groupBy(chatRoom.id)
                        .having(chatParticipant.member.id.countDistinct().goe(2L))
                        .fetchOne()
        );
    }
}
