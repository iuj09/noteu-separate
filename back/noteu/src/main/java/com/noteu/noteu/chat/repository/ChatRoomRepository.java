package com.noteu.noteu.chat.repository;

import com.noteu.noteu.chat.entity.ChatRoom;
import com.noteu.noteu.chat.repository.querydsl.ChatRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom {
}
