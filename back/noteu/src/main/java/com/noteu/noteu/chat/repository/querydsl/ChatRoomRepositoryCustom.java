package com.noteu.noteu.chat.repository.querydsl;

import com.noteu.noteu.chat.entity.ChatParticipant;
import com.noteu.noteu.chat.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {

    List<ChatRoom> findAllBySubjectId(Long subjectId, Long loginId);

    Optional<ChatRoom> existsChatRoom(Long subjectId, Long friendId, Long loginId);
}
