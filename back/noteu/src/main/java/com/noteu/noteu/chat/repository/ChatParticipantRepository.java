package com.noteu.noteu.chat.repository;

import com.noteu.noteu.chat.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
    Optional<ChatParticipant> findByChatRoomIdAndMemberId(Long roomId, Long loginId);
}
