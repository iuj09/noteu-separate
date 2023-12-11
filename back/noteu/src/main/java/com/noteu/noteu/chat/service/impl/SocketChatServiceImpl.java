package com.noteu.noteu.chat.service.impl;

import com.noteu.noteu.chat.converter.ChatConverter;
import com.noteu.noteu.chat.dto.ChatMessageResponse;
import com.noteu.noteu.chat.dto.request.ChatMessageRequestDto;
import com.noteu.noteu.chat.dto.response.ChatMessageResponseDto;
import com.noteu.noteu.chat.entity.ChatMessage;
import com.noteu.noteu.chat.entity.ChatRoom;
import com.noteu.noteu.chat.entity.MessageType;
import com.noteu.noteu.chat.repository.ChatMessageRepository;
import com.noteu.noteu.chat.service.SocketChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocketChatServiceImpl implements SocketChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatConverter converter;

    @Override
    public ChatMessageResponse reqMessage(ChatMessageRequestDto chatMessageRequestDto) {
        log.info("채팅으로 넘어온 값 : {}", chatMessageRequestDto);

        if (MessageType.TALK.equals(chatMessageRequestDto.getMessageType())) {
            ChatMessage save = chatMessageRepository.save(converter.chatMessageRequestDtoToChatMessageEntity(chatMessageRequestDto));
            return converter.chatMessageEntityToChatMessageResponsedto(save);
        }

        chatMessageRequestDto.setMessage(chatMessageRequestDto.getSenderName() + "채팅 입장.");
        return chatMessageRequestDto;
    }
}
