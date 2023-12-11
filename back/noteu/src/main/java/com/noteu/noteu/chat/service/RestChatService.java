package com.noteu.noteu.chat.service;

import com.noteu.noteu.chat.dto.ChatRoomResponse;
import com.noteu.noteu.chat.dto.response.ChatMessageResponseDto;
import com.noteu.noteu.chat.dto.response.ChatRoomInfoResponseDto;
import com.noteu.noteu.chat.dto.response.ChatRoomResponseDto;
import com.noteu.noteu.member.dto.response.MemberResponseDto;

import java.util.List;

public interface RestChatService {

    ChatRoomInfoResponseDto findAllById(Long friendId, Long loginId);

    //채팅방 생성
    List<ChatMessageResponseDto> findPastChat(Long roomId);

    ChatRoomResponse createRoom(Long subjectId, Long friendId, Long loginMemberId);
    //이전 채팅 보임

    List<MemberResponseDto> findAllSubjectsBySubjectId(Long subjectId, Long loginId);
}
