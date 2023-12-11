package com.noteu.noteu.chat.converter;

import com.noteu.noteu.chat.dto.ChatRoomResponse;
import com.noteu.noteu.chat.dto.request.ChatMessageRequestDto;
import com.noteu.noteu.chat.dto.response.ChatMessageResponseDto;
import com.noteu.noteu.chat.dto.response.ChatRoomExistResponseDto;
import com.noteu.noteu.chat.dto.response.ChatRoomResponseDto;
import com.noteu.noteu.chat.entity.ChatMessage;
import com.noteu.noteu.chat.entity.ChatParticipant;
import com.noteu.noteu.chat.entity.ChatRoom;
import com.noteu.noteu.member.dto.response.MemberResponseDto;
import com.noteu.noteu.member.entity.Member;

import java.util.Objects;

public interface ChatConverter {

    default ChatRoomResponseDto chatRoomEntityToChatRoomDto(ChatRoom chatRoom, Long loginId, ChatMessage lastMessage) {
        return ChatRoomResponseDto.builder()
                .id(chatRoom.getId())
                .subjectId(chatRoom.getSubject().getId())
                .lastMessage(lastMessage.getMessage())
                .lastMessageDateTime(lastMessage.getCreatedAt())
                .participants(chatRoom.getParticipants().stream()
                        .map(ChatParticipant::getMember)
                        .filter(m -> !Objects.equals(m.getId(), loginId))
                        .map(this::memberEntityToMemberResponseDto)
                        .toList())
                .build();
    }

    default ChatRoomResponse chatRoomEntityToChatRoomExistResponseDto(ChatRoom chatRoom, Long friendId, Long loginId) {
        return ChatRoomExistResponseDto.builder()
                .loginId(loginId)
                .friendId(friendId)
                .RoomId(chatRoom.getId())
                .build();
    }

    default MemberResponseDto memberEntityToMemberResponseDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .membername(member.getMemberName())
                .introduction(member.getIntroduction())
                .profile(member.getProfile())
                .email(member.getEmail())
                .tel(member.getTel())
                .build();
    }

    default ChatMessage chatMessageRequestDtoToChatMessageEntity(ChatMessageRequestDto chatMessageRequestDto) {
        return ChatMessage.builder()
                .roomId(chatMessageRequestDto.getRoomId())
                .senderId(chatMessageRequestDto.getSenderId())
                .senderName(chatMessageRequestDto.getSenderName())
                .message(chatMessageRequestDto.getMessage())
                .build();
    }

    default ChatMessageResponseDto chatMessageEntityToChatMessageResponsedto(ChatMessage chatMessage) {
        return ChatMessageResponseDto.builder()
                .roomId(chatMessage.getRoomId())
                .senderId(chatMessage.getSenderId())
                .senderName(chatMessage.getSenderName())
                .message(chatMessage.getMessage())
                .createdAt(String.valueOf(chatMessage.getCreatedAt()))
                .build();
    }
}
