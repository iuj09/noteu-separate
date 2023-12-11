package com.noteu.noteu.chat.dto.request;

import com.noteu.noteu.chat.dto.ChatMessageResponse;
import com.noteu.noteu.chat.entity.MessageType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageRequestDto implements ChatMessageResponse {
    MessageType messageType;
    Long roomId;
    Long senderId;
    String senderName;
    String message;
}
