package com.noteu.noteu.chat.dto.response;

import com.noteu.noteu.chat.dto.ChatMessageResponse;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponseDto implements ChatMessageResponse {
    Long roomId;
    Long senderId;
    String senderName;
    String message;
    String createdAt;
}
