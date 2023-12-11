package com.noteu.noteu.chat.dto.response;

import com.noteu.noteu.chat.dto.ChatRoomResponse;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoomExistResponseDto implements ChatRoomResponse {
    Long loginId;
    Long friendId;
    Long RoomId;
}
