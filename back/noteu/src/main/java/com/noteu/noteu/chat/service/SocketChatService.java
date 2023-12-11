package com.noteu.noteu.chat.service;

import com.noteu.noteu.chat.dto.ChatMessageResponse;
import com.noteu.noteu.chat.dto.request.ChatMessageRequestDto;
import com.noteu.noteu.chat.dto.response.ChatMessageResponseDto;

public interface SocketChatService {

    ChatMessageResponse reqMessage(ChatMessageRequestDto chatMessageRequestDto);
}
