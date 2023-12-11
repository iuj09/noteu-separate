package com.noteu.noteu.chat.repository;

import com.noteu.noteu.chat.dto.response.ChatRoomResponseDto;
import com.noteu.noteu.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> getAllByRoomId(Long roomId);

    Optional<ChatMessage> findFirstByRoomIdOrderByCreatedAtDesc(Long roomId);
}